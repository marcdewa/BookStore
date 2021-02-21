package com.example.ezybookstore;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Book  implements Serializable  {
    private int id;
    private String name;
    private String description;
    private int price;
    private String author;
    private String type;
    private String img;
    private Boolean inCart;
    private String category;
    int qty;

    public Book( int id, String name, String description, int price, String author, String type, String img, Boolean inCart, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.author = author;
        this.type = type;
        this.img = img;
        this.inCart = inCart;
        this.category = category;
        this.qty = 1;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setInCart(Boolean inCart) {
        this.inCart = inCart;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public String getAuthor() {
        return author;
    }

    public String getType() {
        return type;
    }

    public String getImg() {
        return img;
    }

    public Boolean getInCart() {
        return inCart;
    }

    public String getCategory() {
        return category;
    }


}
