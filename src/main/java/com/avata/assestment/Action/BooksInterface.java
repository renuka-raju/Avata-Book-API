package com.avata.assestment.Action;

import com.avata.assestment.Resources.Book;

import java.util.List;
import java.util.Map;

public interface BooksInterface {

    public void calculateTopXBooks(Long t,List<Book> books);
    public List<Book> getTopXBooksForTime(int T,int x);
    public List<String> getTopXBookTitles(int T,int x);
    public List<String> getTopXBookSummary(int T,int x);
    public Map<String,Integer> getTitleSimilarityScore(int T, int x);
    public List<String> getTopKeywords(int T, int x,int num);
}
