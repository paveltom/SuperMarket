package DomainLayer;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SupplierController {
    List<Supplier> suppliers;

    public SupplierController(){
        suppliers = new LinkedList<>();
    }

    public void addSupplier(String sid, String bankAccount, boolean cash, boolean credit, Map<String,String> contacts){

        suppliers.add(new Supplier(sid, bankAccount, cash, credit, contacts));
    }


    public void removeSupplier(){

    }



}
