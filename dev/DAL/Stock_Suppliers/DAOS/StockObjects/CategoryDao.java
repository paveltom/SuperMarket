package DAL.Stock_Suppliers.DAOS.StockObjects;

import DAL.Stock_Suppliers.DAOS.DAO;
import StockModule.BusinessLogicLayer.Category;
import java.util.*;

public class CategoryDao extends DAO {

    public void insert(Category c){

        String[] params = {String.valueOf(c.getID()), c.getName()}; //adding subcategories later
        insert("Category", params);
    }

    public void delete(Category c){
        Category parentCategory = c.getParentCategory();
        parentCategory.removeSubCategory(c);

        String[] keys = {"category_id"};
        String[] keysVals = {String.valueOf(c.getID())};
        delete("Category", keys, keysVals);
    }


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
