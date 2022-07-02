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

    public Response addSupplier(String sId, String name, String address, String bankAccount, boolean cash, boolean credit, String contactName, String phoneNum,
                                boolean[] supplyDays, int maxSupplyDays, int supplCycle, boolean deliveryService,
                                String pId, String catNumber, float price) {
        try {
            sc.addSupplier(sId, name, address, bankAccount, cash, credit, contactName, phoneNum,
                    supplyDays, maxSupplyDays, supplCycle, deliveryService,
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

    public ResponseT<boolean[]> getSupplyDays(String sId) {
        try {
            return ResponseT.FromValue(sc.getSupplyDays(sId));
        } catch (Exception e) {
            return ResponseT.FromError(e.getMessage());
        }
    }

    public ResponseT<Integer> getSupplyMaxDays(String sId) {
        try {
            return ResponseT.FromValue(sc.getSupplyMaxDays(sId));
        } catch (Exception e) {
            return ResponseT.FromError(e.getMessage());
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

    public Response setSupplyMaxDays(String sId, int supplyMaxDays) {
        try {
            sc.setMaxSupplyDays(sId, supplyMaxDays);
            return new Response();
        } catch (Exception e) {
            return new Response(e.getMessage());
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

    public Response updateProductCatalogNum(String sId, String oldCatalogNum, String newCatalogNum) {
        try {
            sc.updateCatalogNum(sId, oldCatalogNum, newCatalogNum);
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

    public ResponseT<List<Supplier>> searchProduct(String pId) {
        try {
            return ResponseT.FromValue(sc.searchProduct(pId));
        } catch (Exception e) {
            return ResponseT.FromError(e.getMessage());
        }
    }

    public Response changeDaysOfDelivery(String sId, int day, boolean state) {
        try {
            sc.changeDaysOfDelivery(sId, day, state);
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
