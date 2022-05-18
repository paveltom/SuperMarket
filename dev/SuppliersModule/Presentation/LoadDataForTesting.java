package SuppliersModule.Presentation;

import SuppliersModule.Service.SupplierServices;

public class LoadDataForTesting {
    public LoadDataForTesting(SupplierServices ss){
        boolean[] noSupplydays = {false,false,false,false,false,false,false};

        ss.addSupplier("345tf","000", true, true, "yosi", "0000000000");
        ss.addContract("345tf", noSupplydays, -1, false);

        ss.addProduct("345tf", "1", "tomato", 0.2f);
        ss.addProduct("345tf", "2", "cucember", 0.1f);
        ss.addProduct("345tf", "3", "pineapple", 10f);
        ss.addProduct("345tf", "4", "dragon fruit", 4f);
        ss.addProduct("345tf", "5", "pepper", 0.2f);

        ss.updateDiscount("345tf", "2", 50, 10);
        ss.updateDiscount("345tf", "2", 150, 20);
        ss.updateDiscount("345tf", "3", 10, 5);
        ss.updateDiscount("345tf", "3", 50, 10);
        ss.updateDiscount("345tf", "3", 150, 20);

        ss.addDiscountPerOrder("345tf", "4", 150, 20);
        ss.addDiscountPerOrder("345tf", "5", 50, 2.5f);
        ss.addDiscountPerOrder("345tf", "5", 100, 5);
    }
}
