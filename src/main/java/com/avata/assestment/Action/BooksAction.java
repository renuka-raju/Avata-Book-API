package com.avata.assestment.Action;

import com.avata.assestment.Resources.Book;
import com.avata.assestment.Utils.TFIDFCalculator;
import org.apache.commons.text.similarity.JaccardSimilarity;
import java.util.*;

public class BooksAction implements BooksInterface{

    public static Map<Long,List<Book>> topXbooksMap=new HashMap<>();

    @Override
    public void calculateTopXBooks(Long time, List<Book> books) {
        List<Book> list = new ArrayList<>(books);
        List<Book> maxsizelist = list.subList(0,50);
        Collections.sort(maxsizelist);
        topXbooksMap.put(time,maxsizelist);
    }

    @Override
    public List<Book> getTopXBooksForTime(int t,int x) {
        List<Book> booklist=null;
        long[] startend = calculateEpochFromCounter(t);
        //System.out.println("Epoch for t = "+t+" is : "+startend[0]+" "+startend[1]);
        for(long time: topXbooksMap.keySet()){
            //System.out.println("we have : "+ time);
            if(time>=startend[0]&&time<startend[1]){
                System.out.println("Got it : the epoch we need is : "+time);
                booklist=topXbooksMap.get(time);
                break;
            }
        }
        //System.out.println("Book list empty ? :"+booklist.size());
        return booklist!=null?booklist.subList(0,x):null;
    }

    @Override
    public List<String> getTopXBookTitles(int t,int x) {
        List<Book> booklist=getTopXBooksForTime(t,x);
        if(booklist!=null) {
            List<String> booktitles = new ArrayList<>();
            for (Book book : booklist) {
                booktitles.add(book.getTitle());
            }
            return booktitles;
        }
        return null;
    }

    @Override
    public List<String> getTopXBookSummary(int t,int x) {
        List<Book> booklist=getTopXBooksForTime(t,x);
        if(booklist!=null) {
            List<String> bookSummary = new ArrayList<>();
            for (Book book : booklist) {
                bookSummary.add(book.getSummary());
            }
            return bookSummary;
        }
        return null;
    }

    @Override
    public Map<String,Integer> getTitleSimilarityScore(int t, int x) {
        List<String> titles=getTopXBookTitles(t,x);
        Set<String> goldenfeatureset=new HashSet<>();
        if(titles!=null) {
            for (String title : titles) {
                goldenfeatureset.add(title);
            }
            String goldenfeature=goldenfeatureset.toString();
            System.out.println(goldenfeature);
            JaccardSimilarity js = new JaccardSimilarity();
            Map<String, Integer> similarityscores = new HashMap<>();
            for (String title : titles) {
                int score = (int)(js.apply(goldenfeature, title)*100);
                //System.out.println(title + ":" + score);
                similarityscores.put(title, score);
            }
            return similarityscores;
        }
        return null;
    }

    @Override
    public List<String> getTopKeywords(int t, int x,int num) {
        List<String> summaries=getTopXBookSummary(t,x);
        Set<String> completed=new HashSet<>();
        Map<String,Double> tfidfscores=new HashMap<>();
        TFIDFCalculator calculator=new TFIDFCalculator();
        for(String summary : summaries) {
            String[] wordbag = summary.split(" ");
            for (String word : wordbag) {
                if (!TFIDFCalculator.stopwords.contains(word)&&!completed.contains(word)) {
                    double score=calculator.tfIdf(Arrays.asList(wordbag),summaries,word);
                    tfidfscores.put(word,score);
                }
                completed.add(word);
            }
        }
        List<String> topkeywords=getKeywordsBasedOnScores(tfidfscores);
        if(topkeywords!=null){
            return topkeywords.subList(0,num);
        }
        return null;
    }

    public long[] calculateEpochFromCounter(int t){
        long now = System.currentTimeMillis();
        System.out.println("now :"+now);
        int sec = 4000*t;
        long end=now-sec;
        long start=end-4000;
        return new long[]{start,end};
    }

    private List<String> getKeywordsBasedOnScores(Map<String,Double> tfidf){
        Comparator<Map.Entry<String, Double>> valueComparator = new Comparator<Map.Entry<String,Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> e1, Map.Entry<String, Double> e2) {
                Double v1 = e1.getValue();
                Double v2 = e2.getValue();
                return v2.compareTo(v1);
            }
        };
        Set<Map.Entry<String, Double>> entries = tfidf.entrySet();
        List<Map.Entry<String, Double>> listOfEntries = new ArrayList<>(entries);
        Collections.sort(listOfEntries, valueComparator);
        List<String> sortedByValue = new ArrayList<>();
        for(Map.Entry<String, Double> entry : listOfEntries){
            sortedByValue.add(entry.getKey());
        }
        return sortedByValue;
    }
}
