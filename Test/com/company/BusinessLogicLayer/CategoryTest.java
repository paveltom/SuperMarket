package com.company.BusinessLogicLayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    public Category parent_category;
    public Category subcategory;

    @BeforeEach
    void setUp() {
        parent_category = new Category(1, "Dairy");
        subcategory = new Category(2, "Milk", parent_category);
    }

    @Test
    void getSubCategories() {
        LinkedList<Category> subcategories = (LinkedList<Category>) parent_category.getSubCategories();
        assertEquals(1, subcategories.size());
        assertEquals(subcategory, subcategories.get(0));
    }

    @Test
    void getParentCategory() {
        Category parent = subcategory.getParentCategory();
        assertEquals(parent_category, parent);
    }

    @Test
    void setAsParent() {
        Category newParent = new Category(3, "Meaty");
        subcategory.setAsParent(newParent);
        assertEquals(newParent, subcategory.getParentCategory());
    }
}