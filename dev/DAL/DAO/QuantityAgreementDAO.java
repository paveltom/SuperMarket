package DAL.DAO;

import DAL.DataBaseConnection;
import SuppliersModule.DomainLayer.QuantityAgreement;

public class QuantityAgreementDAO {
    DataBaseConnection conn;

    public QuantityAgreementDAO(){
        conn = new DataBaseConnection();
    }


}
