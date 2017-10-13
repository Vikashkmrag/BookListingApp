package com.example.android.booklisting;

/**
 * Created by vikash on 17/6/17.
 */

public class Book {
    private String mTitle;
    private String mAuthors;
    private String mDate;
    private String mLink;
    Book(String title,String authors,String date,String link){
        mAuthors=authors;
        mDate=date;
        mLink=link;
        mTitle=title;
    }

    public String getTitle(){
        return mTitle;
    }
    public String getAuthors(){
        return mAuthors;
    }
    public String getDate(){
        return mDate;
    }
    public String getLink(){
        return mLink;
    }

}
