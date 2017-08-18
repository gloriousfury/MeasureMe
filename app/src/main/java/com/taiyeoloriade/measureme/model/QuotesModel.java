package com.taiyeoloriade.measureme.model;

public class QuotesModel {


    private Integer id;
    private String quoteText;
    private String quoteAuthor;
    private Integer quoteBackground;


    public QuotesModel() {

    }

    public QuotesModel(String quoteText, String quoteAuthor) {
        this.quoteText = quoteText;
        this.quoteAuthor = quoteAuthor;


    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuoteText() {
        return quoteText;
    }

    public void setQuoteText(String quoteText) {
        this.quoteText = quoteText;
    }

    public String getQuoteAuthor() {
        return quoteAuthor;
    }

    public void setQuoteAuthor(String quoteAuthor) {
        this.quoteAuthor = quoteAuthor;
    }

    public Integer getQuoteBackground() {
        return quoteBackground;
    }

    public void setQuoteBackground(Integer quoteBackground) {
        this.quoteBackground = quoteBackground;
    }

}