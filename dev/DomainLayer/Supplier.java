package DomainLayer;

import java.util.*;

public class Supplier {
    private String sid;
    private String bankAccount;
    private boolean[] paymentMethods;
    private Map<String,String> contacts;

    public Supplier(String sid, String bankAccount, boolean cash, boolean credit, Map<String,String> contacts){
        this.sid = sid;
        this.bankAccount = bankAccount;
        this.paymentMethods = new boolean[2];
        this.paymentMethods[0] = cash;
        this.paymentMethods[1] = credit;
        this.contacts = new HashMap<>();

    }
}
