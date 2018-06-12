package com.avata.assestment.Resources;

public class Book implements Comparable{
    private String author;
    private String summary;
    private String review_score;
    private String title;

    public Book() {
    }

    public Book(String author, String summary, String review_score, String title) {
        this.author = author;
        this.summary = summary;
        this.review_score = review_score;
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public String getSummary() {
        return summary;
    }

    public String getReview_score() {
        return review_score;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Book{" +
                "author='" + author + '\'' +
                ", summary='" + summary + '\'' +
                ", review_score=" + review_score +
                ", title='" + title + '\'' +
                '}';
    }
    @Override
    public int compareTo(Object book) {
        int comparescore=Integer.valueOf(((Book)book).getReview_score());
        return comparescore-Integer.valueOf(this.review_score);
    }


}
