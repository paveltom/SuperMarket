package Presentation;

import Service.SupplierServices;

public class LoadDataForTesting {
    public LoadDataForTesting(SupplierServices ss){
        boolean[] noSupplydays = {false,false,false,false,false,false,false};

        ss.addSupplier("000", true, true, "yosi", "0000000000");
        ss.addContract("0", noSupplydays, -1, false);

        ss.addProduct("0", "1", "tomato", 0.2f);
        ss.addProduct("0", "2", "cucember", 0.1f);
        ss.addProduct("0", "3", "pineapple", 10f);
        ss.addProduct("0", "4", "dragon fruit", 4f);
        ss.addProduct("0", "5", "pepper", 0.2f);

        ss.addDiscountPerItem("0", "2", 50, 10);
        ss.addDiscountPerItem("0", "2", 150, 20);
        ss.addDiscountPerItem("0", "3", 10, 5);
        ss.addDiscountPerItem("0", "3", 50, 10);
        ss.addDiscountPerItem("0", "3", 150, 20);

        ss.addDiscountPerOrder("0", "4", 150, 20);
        ss.addDiscountPerOrder("0", "5", 50, 2.5f);
        ss.addDiscountPerOrder("0", "5", 100, 5);
    }
}
