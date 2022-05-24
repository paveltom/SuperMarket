package SuppliersModule.DomainLayer;

import DAL.DAO.OrderDAO;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Order {

    private final String id;
    private final String supId;
    private final String supName;
    private final String supAddress;
    private final LocalDate date;
    private final String contactPhone;
    private final List<OrderProduct> products = new LinkedList<>();
    private OrderDAO dao;

    //  getters
    public String getId() {
        return id;
    }
    public String getSupId() {
        return supId;
    }
    public String getSupName() {
        return supName;
    }
    public String getSupAddress() {
        return supAddress;
    }
    public LocalDate getDate() {
        return date;
    }
    public String getContactPhone() {
        return contactPhone;
    }
    public List<OrderProduct> getProducts() {
        return products;
    }

    public Order(String id, String supId, String supName, String supAddress, LocalDate date, String contactPhone) {
        dao = new OrderDAO();
        this.id = id;
        this.supId = supId;
        this.supName = supName;
        this.supAddress = supAddress;
        this.date = date;
        this.contactPhone = contactPhone;

        dao.insert(this);
    }
    //for db
    public Order(String id, String supId, String supName, String supAddress, LocalDate date, String contactPhone, boolean isFromDB) {
        dao = new OrderDAO();
        this.id = id;
        this.supId = supId;
        this.supName = supName;
        this.supAddress = supAddress;
        this.date = date;
        this.contactPhone = contactPhone;
    }

    public void addProduct(String pId, float catalogPrice, int amount, float discount, float finalPrice){
        if(hasProduct(pId))
            throw new IllegalArgumentException("product already exists in the order");
        products.add(new OrderProduct(supId, id, pId,catalogPrice,amount,discount,finalPrice));
    }

    public void changeProduct(String pId, int amount, float discount, float finalPrice){
        Optional<OrderProduct> op =  products.stream().filter(OrderProduct -> OrderProduct.getId().equals(pId)).findFirst();
        if(op.isPresent())
            op.get().update(amount, discount, finalPrice);
        else
            throw new IllegalArgumentException("product doesn't exist in the order");
    }

    public boolean hasProduct(String pId){
        return products.stream().anyMatch(OrderProduct -> OrderProduct.getId().equals(pId));
    }

    public int getQuantity(String pId){
        Optional<OrderProduct> op =  products.stream().filter(OrderProduct -> OrderProduct.getId().equals(pId)).findFirst();
        return op.map(OrderProduct::getAmount).orElse(0);
    }
}
