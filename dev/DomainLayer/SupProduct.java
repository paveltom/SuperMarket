package com.company.DomainLayer;

public class SupProduct {
    private String catalogNum;
    private String name;
    private float price;

    public SupProduct(String catalogNum, String name, float price){
        setCatalogNum(catalogNum);
        setName(name);
        setPrice(price);
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public String getCatalogNum() {
        return catalogNum;
    }

    public void setCatalogNum(String catalogNum) { this.catalogNum = catalogNum; }

    public void setName(String name) { this.name = name;}

    public void setPrice(float price) {
        if (price < 0 )
            throw new IllegalArgumentException("product price cannot be negative");

        this.price = price;
    }
}
