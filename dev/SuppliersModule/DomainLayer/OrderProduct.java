package SuppliersModule.DomainLayer;

import DAL.DAO.OrderProductDAO;

public class OrderProduct {
    private final String id;
    private final float catalogPrice;
    private int amount;
    private float discount;
    private float finalPrice;
    private String sId;
    private String oId;
    private OrderProductDAO dao;

    // getters and setters
    public String getsId(){return sId;}
    public String getoId(){return oId;}
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
        dao.setAmount(this);
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        if(amount < 0 )
            throw new IllegalArgumentException("amount of product in a order can't be negative");
        this.discount = discount;

        dao.setDiscount(this);
    }

    public float getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(float finalPrice) {
        if(amount <= 0 )
            throw new IllegalArgumentException("price of product must be positive");
        this.finalPrice = finalPrice;
        dao.setFinalPrice(this);
    }

    public OrderProduct(String sId, String oId, String id, float catalogPrice, int amount, float discount, float finalPrice) {
        if(catalogPrice <= 0 )
            throw new IllegalArgumentException("price of product must be positive");

        this.id = id;
        this.catalogPrice = catalogPrice;
        this.amount = amount;
        this.discount = discount;
        this.finalPrice = finalPrice;
        dao = new OrderProductDAO();
    }

    public void update( int amount, float discount, float finalPrice){
        setAmount(amount);
        setDiscount(discount);
        setFinalPrice(finalPrice);
    }

}
