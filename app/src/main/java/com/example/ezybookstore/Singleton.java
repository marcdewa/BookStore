package com.example.ezybookstore;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Singleton implements Cloneable {
    private static Singleton mInstance= null;

    private ArrayList<Book> books ;

    private ArrayList<Book> orderedBooks ;

    public ArrayList<String> uniqueCategory;

    int subTotal;
    int Total;
    int Taxes;

    protected Singleton(){
        books = new ArrayList<>();
        orderedBooks =new ArrayList<>();
        uniqueCategory = new ArrayList<>();
    }

    public static synchronized Singleton getInstance() {
        if(null == mInstance){
            mInstance = new Singleton();
        }
        return mInstance;
    }

    public ArrayList<Book> getBooks(){
        return books;
    }

    public ArrayList<Book> getOrderedBooks(){
        return orderedBooks;
    }
    public void addOrderedBooks(int i){
        orderedBooks.add(books.get(i));
    }
    public void countSubTotal(){
        int sub = 0;
        for(int i =0;i<orderedBooks.size();i++){
            sub+=orderedBooks.get(i).getPrice()*orderedBooks.get(i).qty;
            Log.d("Test11",""+sub+"qty: "+orderedBooks.get(i).qty);
        }
        this.subTotal = sub;
    }
    public void countTaxes(){
        this.Taxes = this.subTotal/10;
    }

    public void countTotal(){
        this.Total = Taxes+subTotal;
    }
    @NonNull
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void clearOrderedBooks(){
        this.orderedBooks = new ArrayList<>();
    }

    public Boolean isOrderEmpty(){
        return orderedBooks.isEmpty();
    }
}
