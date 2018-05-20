package com.example.android.newsapp;

public class Monkey {

    // Date of the article
    private String mDate;

    // Title of the article
    private String mTitle;

    // Section name of the article
    private String mSection;

    // URL to open for the article
    private String mUrl;

    /**
     * create a new Monkey object
     * @param Date is the date of the article
     * @param Title is the title of the article on the web
     * @param Section is the section name for the article
     * @param Url is the URL for the whole article online
     */

    public Monkey(String Date, String Title, String Section, String Url){
        mDate = Date;
        mTitle = Title;
        mSection = Section;
        mUrl = Url;
    }

    /**
     * Now we make public variables that can be used to fetch this data
     */

    public String getDate() {
        return mDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSection() {
        return mSection;
    }

    public String getUrl() {
        return mUrl;
    }
}

