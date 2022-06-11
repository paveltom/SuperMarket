package StockModule.BusinessLogicLayer;

import DAL.DAOS.StockObjects.CategoryDao;

import java.util.LinkedList;
import java.util.List;

public class Category {
    private final int ID;
    private String name;
    private Category parentCategory;
    private List<Category> subCategories;
    private CategoryDao dao;


    Category(int _ID,String _name)
    {//Constructor for 1st-degree category
        dao = new CategoryDao();
        ID = _ID;
        name = _name;
        parentCategory = null;
        subCategories = new LinkedList<Category>();

        dao.insert(this);
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

        dao.setParent(this);
        Parent.dao.updateSubCategories(Parent);
    }

    public int getID(){
        return ID;
    }

    public String getName() {
        return this.name;
    }

    public void removeSubCategory(Category subCategory){
        subCategories.remove(subCategory);
        dao.updateSubCategories(this);
    }

}
