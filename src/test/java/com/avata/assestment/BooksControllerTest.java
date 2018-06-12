package com.avata.assestment;

import com.avata.assestment.Action.BooksAction;
import com.avata.assestment.Controller.BooksController;
import com.avata.assestment.Resources.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BooksController.class, secure = false)
public class BooksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BooksAction bookAction;

    @Test
    public void testListTopBooks() throws Exception{

        Book b1=new Book("Shakespeare","Classic play","78","Hamlet");
        Book b2=new Book("Paulo Coelho","Philosophy, fantasy fiction","68","Alchemist ");
        List<Book> books=new ArrayList<Book>();
        books.add(b1);
        books.add(b2);

        Mockito.when(
                bookAction.getTopXBooksForTime(Mockito.anyInt(),
                        Mockito.anyInt())).thenReturn(books);
    }

    @Test
    public void testListBooksSimilarity() throws Exception{

        Map<String,Integer> booksandreviewscores=new HashMap<>();
        booksandreviewscores.put("Hamlet",78);
        booksandreviewscores.put("Alchemist",68);

        Mockito.when(
                bookAction.getTitleSimilarityScore(Mockito.anyInt(),
                        Mockito.anyInt())).thenReturn(booksandreviewscores);
    }

    @Test
    public void testlistTopKeywords() throws Exception{

        List<String> keywords=new ArrayList<>();
        keywords.add("mountain");
        keywords.add("iron");
        keywords.add("maze");
        Mockito.when(
                bookAction.getTopKeywords(Mockito.anyInt(),
                        Mockito.anyInt(),Mockito.anyInt())).thenReturn(keywords);
    }
}
