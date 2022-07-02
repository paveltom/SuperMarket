package DAL.DAOS.StockObjects;

import DAL.DAOS.DAO;
//import DAL.IdentityMaps.CategoryIdentityMap;
import StockModule.BusinessLogicLayer.Category;
import StockModule.BusinessLogicLayer.Item;

import java.lang.reflect.Array;
import java.util.*;

public class CategoryDao extends DAO {
//    private CategoryIdentityMap categoryIdentityMap;

//    public CategoryDao(){ categoryIdentityMap = CategoryIdentityMap.getInstance(); }

    public void insert(Category c){

        String[] params = {String.valueOf(c.getID()), c.getName()}; //adding subcategories later
        insert("Category", params);
//        categoryIdentityMap.cache(c);
    }

    public void delete(Category c){
        Category parentCategory = c.getParentCategory();
        parentCategory.removeSubCategory(c);

        String[] keys = {"category_id"};
        String[] keysVals = {String.valueOf(c.getID())};
        delete("Category", keys, keysVals);
    }

//    public void updateSubCategories(Category c){
//        List<Category> subC = c.getSubCategories();
//        String s = "";
//        for (Category cat : subC){
//            s += ", " + cat;
//        }
//
//        setAttribute(c, "subCategoreis", s);
//    }

    public void setParent(Category c){
        //setAttribute(c, "parentCategory", String.valueOf(c.getParentCategory().getID()));
        addRelation(c.getParentCategory().getID(), c.getID());
    }

    private void setAttribute(Category c, String attribute, String value){
        String[] keys = {"category_id"};
        String[] keysVals = {String.valueOf(c.getID())};
        update("Category", keys, keysVals, attribute, value);
    }

    public void addRelation(int parentId, int chileId){
        String[] params = {String.valueOf(parentId), String.valueOf(chileId)};
        insert("CategoryRelations", params);
    }

    public int[][] loadRelations(){

        List<String[]> sFromDB = load("CategoryRelations", null, null);
        int[][] relations = new int[sFromDB.size()][2];

        int i = 0;
        for(String[] s : sFromDB){
            relations[i][0] = Integer.valueOf(s[0]);
            relations[i][1] = Integer.valueOf(s[1]);
        }

        return relations;
    }
}
