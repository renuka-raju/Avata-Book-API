package com.avata.assestment.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avata.assestment.Action.BooksAction;
import com.avata.assestment.Resources.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BooksController {

    @Autowired
    BooksAction booksaction;
    @RequestMapping(value = "/book/{epoch}/{topx}", method = RequestMethod.GET)
    public ResponseEntity<Map<String,String>> listTopBooks(@PathVariable("epoch") int t,@PathVariable("topx") int x) {
        List<Book> books = booksaction.getTopXBooksForTime(t,x);
        if (books==null||books.isEmpty()) {
            System.out.println("Book list not available for the given epoch yet:"+t);
            System.out.println("Try for a more recent timeframe");
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        Map<String,String> bookscore=new HashMap<>();
        for(Book book : books){
            bookscore.put(book.getTitle(),book.getReview_score());
        }
        return new ResponseEntity<>(bookscore, HttpStatus.OK);
    }

    @RequestMapping(value = "/book/similarity/{epoch}/{topx}", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Integer>> listBooksSimilarity(@PathVariable("epoch") int t,@PathVariable("topx") int x) {
        Map<String,Integer> similarities = booksaction.getTitleSimilarityScore(t,x);
        if (similarities==null||similarities.isEmpty()) {
            System.out.println("Book list not available for the given epoch yet:"+t);
            System.out.println("Try for a more recent timeframe");
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(similarities, HttpStatus.OK);
    }

    @RequestMapping(value = "/book/keyword/{epoch}/{topx}/{noofkeywords}", method = RequestMethod.GET)
    public ResponseEntity<List<String>> listBooksSimilarity(@PathVariable("epoch") int t,@PathVariable("topx") int x,@PathVariable("noofkeywords") int num) {
        List<String> keywords = booksaction.getTopKeywords(t,x,num);
        if (keywords==null||keywords.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(keywords, HttpStatus.OK);
    }
}