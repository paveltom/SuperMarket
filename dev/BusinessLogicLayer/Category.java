package com.company.BusinessLogicLayer;

import java.util.LinkedList;
import java.util.List;

public class Category {
    private final int ID;
    private String name;
    private Category parentCategory;
    private List<Category> subCategories;


    Category(int _ID,String _name)
    {//Constructor for 1st-degree category
        ID = _ID;
        name = _name;
        parentCategory = null;
        subCategories = new LinkedList<Category>();
    }

    public String toString(){
        return "Category ID : " + ID + " , Category name : " + name + " "+ "\n";
    }

    Category(int _ID,String _name,Category _parentCategory)
    {//Constructor for non 1st-degree category
        this(_ID, _name);
        parentCategory=_parentCategory;
    }

    public List<Category> getSubCategories(){
        return subCategories;
    }
    public Category getParentCategory()
    {
        return parentCategory;
    }

    public void setAsParent(Category Parent)
    {
        this.parentCategory = Parent;
        Parent.subCategories.add(this);
    }

    public int getID(){
        return ID;
    }
}
