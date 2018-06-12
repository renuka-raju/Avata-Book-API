package com.avata.assestment.CacheLayer;

import com.avata.assestment.Action.BooksAction;
import com.avata.assestment.Resources.Book;
import java.util.*;

public class InMemoryStorage {
    private static InMemoryStorage inMemoryStorage;
    private int capacity;
    private static Map<Long,List<Book>> booksmap=new TreeMap<>();//order by key - timestamp
    private static BooksAction booksaction;

    public static synchronized InMemoryStorage getInstance() {
        if(inMemoryStorage==null){
            inMemoryStorage=new InMemoryStorage();
            booksaction=new BooksAction();
        }
        return inMemoryStorage;
    }

    public void saveBooksMap(List<Book> books, long time){
        if(booksmap.size()<capacity) {
            booksmap.put(time, books);
        }
        else{
            Set<Long> keys=booksmap.keySet();
            Iterator iter = keys.iterator();
            Long firstkey=(Long)iter.next();
            booksmap.remove(firstkey);
            booksmap.put(time,books);
        }
        booksaction.calculateTopXBooks(time,books);
        System.out.println("We have book lists for "+ booksmap.size() +" timestamps behind from the current time");
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public static Map<Long, List<Book>> getBooksmap() {
        return booksmap;
    }
}
