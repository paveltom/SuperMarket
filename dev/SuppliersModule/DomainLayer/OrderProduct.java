package com.company.SuppliersModule.DomainLayer;

public class OrderProduct {
    private final String id;
    private final float catalogPrice;
    private int amount;
    private float discount;
    private float finalPrice;

    // getters and setters
    public String getId() {
        return id;
    }

    public float getCatalogPrice() {
        return catalogPrice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        if(amount <= 0 )
            throw new IllegalArgumentException("amount of product in a order must be positive");
        this.amount = amount;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        if(amount < 0 )
            throw new IllegalArgumentException("amount of product in a order can't be negative");
        this.discount = discount;
    }

    public float getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(float finalPrice) {
        if(amount <= 0 )
            throw new IllegalArgumentException("price of product must be positive");
        this.finalPrice = finalPrice;
    }

    public OrderProduct(String id, float catalogPrice, int amount, float discount, float finalPrice) {
        if(catalogPrice <= 0 )
            throw new IllegalArgumentException("price of product must be positive");

        this.id = id;
        this.catalogPrice = catalogPrice;
        this.amount = amount;
        this.discount = discount;
        this.finalPrice = finalPrice;
    }

    public void update( int amount, float discount, float finalPrice){
        setAmount(amount);
        setDiscount(discount);
        setFinalPrice(finalPrice);
    }

}
