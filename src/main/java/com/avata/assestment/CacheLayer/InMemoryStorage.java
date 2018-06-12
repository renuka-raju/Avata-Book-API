package com.avata.assestment.CacheLayer;

import com.avata.assestment.Action.BooksAction;
import com.avata.assestment.Resources.Book;
import java.util.*;

public class InMemoryStorage {
    private static InMemoryStorage inMemoryStorage;
    public static Map<Long,List<Book>> booksmap=new TreeMap<>();
    private static BooksAction booksaction;
    public static synchronized InMemoryStorage getInstance() {
        if(inMemoryStorage==null){
            inMemoryStorage=new InMemoryStorage();
            booksaction=new BooksAction();
        }
        return inMemoryStorage;
    }
    public void saveBooksMap(List<Book> books, long time){
        if(booksmap.size()<50) {
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
        System.out.println(booksmap.size());
    }

    public static Map<Long, List<Book>> getBooksmap() {
        return booksmap;
    }
}
