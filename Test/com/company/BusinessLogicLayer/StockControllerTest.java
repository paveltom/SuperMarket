package com.company.BusinessLogicLayer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class StockControllerTest {

    public StockController controller;
    @BeforeEach
    void setUp(){
        controller = new StockController();
    }

    @Test
    void insertNewProduct() {
        controller.insertNewProduct("Milk", "Tnuva", 1, new Date(), 10);
        assertEquals(1, controller.getProductsInStock().size());
        assertEquals(1, controller.getProductsInStock().get(0).getCategoryID());
    }

    @Test
    void insertNewCategory() {
        controller.insertNewCategory("Diary");
        assertEquals("Dairy", controller.getCategories().get(0).getName());
    }

    @Test
    void insertNewDiscount() {
        controller.insertNewDiscount(1, new Date(2022, Calendar.APRIL, 25), new Date(2022, Calendar.APRIL, 26), 10, Type.FIXED);
        assertEquals(Type.FIXED, controller.getCurrentDiscounts().get(0).getType().get(10));
    }

    @Test
    void deleteProduct() {
        controller.insertNewProduct("Milk", "Tnuva", 1, new Date(), 10);
        controller.deleteProduct(controller.getProductsInStock().size() - 1);
        assertEquals(0, controller.getProductsInStock().size());
    }

    @Test
    void deleteCategory() {
        controller.insertNewCategory("Diary");
        controller.deleteCategory(controller.getCategories().size() - 1);
        assertEquals(0, controller.getCategories().size());
    }

    @Test
    void deleteDiscount() {
        controller.insertNewDiscount(1, new Date(2022, Calendar.APRIL, 25), new Date(2022, Calendar.APRIL, 26), 10, Type.FIXED);
        controller.deleteCategory(controller.getCurrentDiscounts().size() - 1);
        assertEquals(0, controller.getCurrentDiscounts().size());
    }

    @Test
    void deletePurchase() {
        controller.insertNewPurchase(new Date(2022, Calendar.APRIL, 25), 1, 10, 10);
        controller.deletePurchase(controller.getPurchasesHistoryReport().size() - 1);
        assertEquals(0, controller.getPurchasesHistoryReport().size());
    }
}