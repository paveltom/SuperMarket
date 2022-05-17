package SuppliersModule.Service;

import SuppliersModule.DomainLayer.*;

import java.util.Dictionary;
import java.util.List;
import java.util.Map;

public class SupplierServices {

    private SupplierController sc;

    public SupplierServices(){
        sc = new SupplierController();
    }

    public ResponseT<List<Supplier>> getSuppliers(){
        try{
            return ResponseT.FromValue(sc.getSuppliers());
        }catch (Exception e){
            return ResponseT.FromError(e.getMessage());
        }
    }

    public ResponseT<Contract> getSupplierContract(String suppId){
        try{
            return ResponseT.FromValue(sc.getSupplierContract(suppId));
        }catch (Exception e){
            return ResponseT.FromError(e.getMessage());
        }
    }

    public Response addSupplier(String supId, String bankAccount, boolean cash, boolean credit, String contactName, String contactNum){
        try{
            sc.addSupplier(supId, bankAccount, cash, credit, contactName, contactNum);
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    public Response removeSupplier(String sid){
        try{
            sc.removeSupplier(sid);
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    public Response addContact(String sid, String contactName, String phoneNum){
        try{
            sc.addContact(sid, contactName, phoneNum);
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    public Response addContract(String sid, boolean[] supplyDays, int supplyMaxDays, boolean deliveryService){
        try{
            sc.addContract(sid, supplyDays, supplyMaxDays, deliveryService);
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    public ResponseT<boolean[]> getSupplyDays(String sid){
        try{
            return ResponseT.FromValue(sc.getSupplyDays(sid));
        }catch (Exception e){
            return ResponseT.FromError(e.getMessage());
        }
    }

    public ResponseT<Integer> getSupplyMaxDays(String sid){
        try{
            return ResponseT.FromValue(sc.getSupplyMaxDays(sid));
        }catch (Exception e){
            return ResponseT.FromError(e.getMessage());
        }
    }

    public ResponseT<Boolean> hasDeliveryService(String sid){
        try{
            return ResponseT.FromValue(sc.hasDeliveryService(sid));
        }catch (Exception e){
            return ResponseT.FromError(e.getMessage());
        }
    }

    public ResponseT<List<SupProduct>> getCatalog(String sid){
        try{
            return ResponseT.FromValue(sc.getCatalog(sid));
        }catch (Exception e){
            return ResponseT.FromError(e.getMessage());
        }
    }

    public ResponseT<QuantityAgreement> getQa(String sid){
        try{
            return ResponseT.FromValue(sc.getQa(sid));
        }catch (Exception e){
            return ResponseT.FromError(e.getMessage());
        }
    }

    public Response setSupplyDays(String sid, boolean[] supplyDays){
        try{
            sc.setSupplyDays(sid, supplyDays);
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    public Response setSupplyMaxDays(String sid, int supplyMaxDays){
        try{
            sc.setSupplyMaxDays(sid, supplyMaxDays);
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    public Response setDeliveryService(String sid, boolean deliveryService){
        try{
            sc.setDeliveryService(sid, deliveryService);
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    public Response addProduct(String sid, String catalogNum, String name, float price) {
        try{
            sc.addProduct(sid, catalogNum, name, price);
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    public Response removeProduct(String sid, String catalogNum) {
        try{
            sc.removeProduct(sid, catalogNum);
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    public Response updateProductCatalogNum(String sid, String oldCatalogNum, String newCatalogNum) {
        try{
            sc.updateProductCatalogNum(sid, oldCatalogNum, newCatalogNum);
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    public Response updateProductName(String sid, String catalogNum, String name) {
        try {
            sc.updateProductName(sid, catalogNum, name);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response updateProductPrice(String sid, String catalogNum, float price) {
        try{
            sc.updateProductPrice(sid, catalogNum, price);
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    // Quantity Agreement methods
    public Response addDiscountPerItem(String sid, String productID, int quantity, float discount){
        try{
            sc.addDiscountPerItem(sid, productID, quantity, discount);
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    public Response addDiscountPerOrder(String sid, String productID, int quantity, float discount){
        try{
            sc.addDiscountPerOrder(sid, productID, quantity, discount);
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    public Response updateDiscountPerItem(String sid, String productID, int quantity, float discount){
        try{
            sc.updateDiscountPerItem(sid, productID, quantity, discount);
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    public Response updateDiscountPerOrder(String sid, String productID, int quantity, float discount){
        try{
            sc.updateDiscountPerOrder(sid, productID, quantity, discount);
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    public Response removeDiscountPerItem(String sid, String productID, int quantity){
        try{
            sc.removeDiscountPerItem(sid, productID, quantity);
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    public Response removeDiscountPerOrder(String sid, String productID, int quantity) {
        try{
            sc.removeDiscountPerOrder(sid, productID, quantity);
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    public ResponseT<Dictionary<Integer,Float>> getDiscountsForProductPerItem(String sid, String productID){
        try{
            return ResponseT.FromValue(sc.getDiscountsForProductPerItem(sid, productID));
        }catch (Exception e){
            return ResponseT.FromError(e.getMessage());
        }
    }

    public ResponseT<Dictionary<Integer,Float>> getDiscountsForProductPerOrder(String sid, String productID){
        try{
            return ResponseT.FromValue(sc.getDiscountsForProductPerOrder(sid, productID));
        }catch (Exception e){
            return ResponseT.FromError(e.getMessage());
        }
    }

    public ResponseT<List<SupProduct>> searchProduct(String name){
        try{
            return ResponseT.FromValue(sc.searchProduct(name));
        }catch (Exception e){
            return ResponseT.FromError(e.getMessage());
        }
    }










    public ResponseT<Map<String,String>> getSupplierContacts(String sid){
        try{
            return ResponseT.FromValue(sc.getSupplierContacts(sid));
        }catch (Exception e){
            return ResponseT.FromError(e.getMessage());
        }
    }

    public Response removeContact(String sid, String name){
        try{
            sc.removeContact(sid, name);
            return new Response();
        }catch (Exception e){
            return new Response(e.getMessage());
        }
    }

    public ResponseT<Dictionary<String, Dictionary<Integer, Float>>> getPerItem(String sid) {
        try{
            return ResponseT.FromValue(sc.getPerItem(sid));
        }catch (Exception e){
            return ResponseT.FromError(e.getMessage());
        }
    }

    public ResponseT<Dictionary<String, Dictionary<Integer, Float>>> getPerOrder(String sid) {
        try{
            return ResponseT.FromValue(sc.getPerOrder(sid));
        }catch (Exception e){
            return ResponseT.FromError(e.getMessage());
        }
    }
}
