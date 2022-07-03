package SuppliersModule.Service;


import SuppliersModule.DomainLayer.*;
import java.util.List;
import java.util.Map;

public class SupplierServices {

    private final SupplierController sc = SupplierController.getInstance();

    public ResponseT<List<Supplier>> getSuppliers() {
        try {
            return ResponseT.FromValue(sc.getSuppliers());
        } catch (Exception e) {
            return ResponseT.FromError(e.getMessage());
        }
    }

    public ResponseT<Contract> getContract(String suppId) {
        try {
            return ResponseT.FromValue(sc.getContract(suppId));
        } catch (Exception e) {
            return ResponseT.FromError(e.getMessage());
        }
    }

    public Response addSupplier(String sId, String name, String address, String bankAccount, boolean cash, boolean credit, boolean[] workingDays,
                                String contactName, String phoneNum,
                                boolean[] orderingDays, int supplCycle,
                                String pId, String catNumber, float price) {
        try {
            sc.addSupplier(sId, name, address, bankAccount, cash, credit, workingDays,
                    contactName, phoneNum,
                    orderingDays, supplCycle,
                    pId, catNumber, price);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response removeSupplier(String sId) {
        try {
            sc.removeSupplier(sId);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response addContact(String sId, String contactName, String phoneNum) {
        try {
            sc.addContact(sId, contactName, phoneNum);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public ResponseT<Map<String, String>> getContacts(String sId) {
        try {
            return ResponseT.FromValue(sc.getContacts(sId));
        } catch (Exception e) {
            return ResponseT.FromError(e.getMessage());
        }
    }

    public Response removeContact(String sId, String name) {
        try {
            sc.removeContact(sId, name);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public ResponseT<List<CatalogProduct>> getCatalog(String sId) {
        try {
            return ResponseT.FromValue(sc.getCatalog(sId));
        } catch (Exception e) {
            return ResponseT.FromError(e.getMessage());
        }
    }

    public ResponseT<QuantityAgreement> getQa(String sId) {
        try {
            return ResponseT.FromValue(sc.getQa(sId));
        } catch (Exception e) {
            return ResponseT.FromError(e.getMessage());
        }
    }


    public Response addProduct(String sId, String pId, String catalogNum, float price) {
        try {
            sc.addProduct(sId, pId, catalogNum, price);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response removeProduct(String sId, String catalogNum) {
        try {
            sc.removeProduct(sId, catalogNum);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response updateProductCatalogNum(String sId, String pid, String newCatalogNum) {
        try {
            sc.updateCatalogNum(sId, pid, newCatalogNum);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response updateProductPrice(String sId, String catalogNum, float price) {
        try {
            sc.updateProductPrice(sId, catalogNum, price);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    // Quantity Agreement methods
    public Response updateDiscount(String sId, String pId, int quantity, float discount) {
        try {
            sc.updateDiscount(sId, pId, quantity, discount);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public ResponseT<Map<Integer, Float>> getDiscounts(String sId, String pId) {
        try {
            return ResponseT.FromValue(sc.getDiscounts(sId, pId));
        } catch (Exception e) {
            return ResponseT.FromError(e.getMessage());
        }
    }

    public ResponseT<Map<String, Map<Integer, Float>>> getDiscounts(String sId) {
        try {
            return ResponseT.FromValue(sc.getDiscounts(sId));
        } catch (Exception e) {
            return ResponseT.FromError(e.getMessage());
        }
    }

    public Response setWeeklyOrdering(String sId, boolean[] days) {
        try {
            sc.changeWeeklyOrdering(sId, days);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public Response setSupplyCycle(String suppId, int parseInt) {
        try {
            sc.setSupplyCycle(suppId, parseInt);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    public ResponseT<List<Order>> getOrders(String sId) {
        try {
            return ResponseT.FromValue(sc.getOrders(sId));
        } catch (Exception e) {
            return ResponseT.FromError(e.getMessage());
        }
    }

    public Response endDay() {
        try {
            sc.endDay();
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }
}
