package com.axel.akcosmetics.Model;

public class ProductCategory {

    private String categoryId;
    private String categoryname;

    public ProductCategory(String categoryId, String categoryname) {
        this.categoryId = categoryId;
        this.categoryname = categoryname;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryname() {
        return categoryname;
    }
}
