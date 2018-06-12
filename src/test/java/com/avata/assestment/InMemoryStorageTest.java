package com.avata.assestment;

import com.avata.assestment.CacheLayer.InMemoryStorage;
import com.avata.assestment.Resources.Book;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryStorageTest {
    Map<Long,List<Book>> expected=new HashMap<>();

    @Test
    public void testSaveBooksMap(){
        InMemoryStorage.getInstance().setCapacity(2);

        long time1=System.currentTimeMillis();
        Book b1=new Book("Shakespeare","Classic play","78","Hamlet");
        Book b2=new Book("Paulo Coelho","Philosophy, fantasy fiction","68","Alchemist ");
        List<Book> books1=new ArrayList<Book>();
        books1.add(b1);
        books1.add(b2);
        expected.put(time1,books1);
        InMemoryStorage mem=InMemoryStorage.getInstance();
        mem.saveBooksMap(books1,time1);

        long time2=time1+4000;
        Book b3=new Book("Dan Brown","Thriller, Fiction","80","Da Vinci Code");
        Book b4=new Book("Arundati Roy","Philosophy, Drama","90","The God of Small Things ");
        List<Book> books2=new ArrayList<Book>();
        books2.add(b3);
        books2.add(b4);
        expected.put(time2,books2);

        mem.saveBooksMap(books2,time2);
        Assert.assertEquals(expected,mem.getBooksmap());

        Book b5=new Book("Conan Dyle","Adeventure, Detective","70","Sherlock Holmes");
        Book b6=new Book("Ruskin Bonf","Fiction","75","Angry River");
        List<Book> books3=new ArrayList<Book>();
        books3.add(b5);
        books3.add(b6);
        long time3=time2+4000;
        expected.put(time3,books3);
        mem.saveBooksMap(books3,time3);

        expected.remove(time1);
        Assert.assertEquals(expected,mem.getBooksmap());

    }
}
