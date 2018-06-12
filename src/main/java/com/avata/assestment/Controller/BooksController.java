package com.avata.assestment.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avata.assestment.Action.BooksAction;
import com.avata.assestment.Resources.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BooksController {

    @RequestMapping(value = "/book/{t}/{x}", method = RequestMethod.GET)
    public ResponseEntity<Map<String,String>> listTopBooks(@PathVariable("t") int t,@PathVariable("x") int x) {
        List<Book> books = new BooksAction().getTopXBooksForTime(t,x);
        if (books==null||books.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        Map<String,String> bookscore=new HashMap<>();
        for(Book book : books){
            bookscore.put(book.getTitle(),book.getReview_score());
        }
        return new ResponseEntity<Map<String,String>>(bookscore, HttpStatus.OK);
    }

    @RequestMapping(value = "/book/similarity/{t}/{x}", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Integer>> listBooksSimilarity(@PathVariable("t") int t,@PathVariable("x") int x) {
        Map<String,Integer> similarities = new BooksAction().getTitleSimilarityScore(t,x);
        if (similarities==null||similarities.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Map<String,Integer>>(similarities, HttpStatus.OK);
    }

    @RequestMapping(value = "/book/keyword/{t}/{x}/{num}", method = RequestMethod.GET)
    public ResponseEntity<List<String>> listBooksSimilarity(@PathVariable("t") int t,@PathVariable("x") int x,@PathVariable("num") int num) {
        List<String> keywords = new BooksAction().getTopKeywords(t,x,num);
        if (keywords==null||keywords.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<String>>(keywords, HttpStatus.OK);
    }
}