package DAL;

import DAL.DAObjects.*;
import DAL.DTO.DeliveryRecipeDTO;
import DAL.DTO.DriverDTO;
import DAL.DTO.TruckDTO;
import DAL.DTO.WorkerDTO;

import java.util.List;

public class DALController {

    private DriversDAO driversDAO;
    private TrucksDAO trucksDAO;
    private DeliveriesDAO deliveriesDAO;
    private WorkersDAO workersDAO;
    private ShiftsDAO shiftsDAO;

    private static class DALControllerHolder {
        private static DALController instance = new DALController();
    }

    private DALController(){
        driversDAO = new DriversDAO();
        trucksDAO = new TrucksDAO();
        deliveriesDAO = new DeliveriesDAO();
        workersDAO = new WorkersDAO();
        shiftsDAO = new ShiftsDAO();
    }

    public static DALController getInstance(){
        return DALControllerHolder.instance;
    }

    // delivery module methods:

    public List<DriverDTO> getAllDrivers(){

    }

    public List<TruckDTO> getAllTrucks(){

    }

    public List<DeliveryRecipeDTO> getAllDeliveries(){

    }

    public DriverDTO getDriver(String key){

    }

    public TruckDTO getTruck(String key){

    }

    public DeliveryRecipeDTO getDelivery(String key){

    }

    public void updateDriverDiary(String key, String shifts){

    }

    public void updateTruckDiary(String key, String shifts){

    }

     public void addDelivery(String orderID, String supplier, String client, String milkProduct, String subDate){

     }

    public void addTruck(TruckDTO truckToAdd){

    }

    public void removeDelivery(DeliveryRecipeDTO deliveryDelete){

    }

    public void removeTruck(long key){

    }

    public void removeDriver(String key){

    }

    // public void addDriverFutureShifts(String key, String toAdd)
    // rewriteDriverFutureShifts
    // public void getDriverFutureShifts(String key)






    // ====================================================================================
    // personnel module methods:

    public WorkerDTO getWorker(String _id)
    {
        return workersDAO.getWorker(_id);
    }
    public List<WorkerDTO> getAllWorkers()
    {
        return workersDAO.getAllWorkers();
    }

    public List<WorkerDTO> getWorkerByJob(String _Job)
    {
        return workersDAO.getWorkerByJob(_Job);
    }

    public void UpdateWorker(String _id,String field,String _data)
    {
        workersDAO.UpdateWorker(_id,field,_data);
    }

    public void DeleteWorker(String _id)
    {
        workersDAO.DeleteWorker(_id);
    }
    public void DeleteDriver(String _id)
    {
        driversDAO.DeleteDriver(_id);
    }


    // =====================both modules methods:

    public void clearCache(String typeToClear){
        switch (typeToClear){
            case "drivers":
                driversDAO.freeCache();
                break;
            case "trucks":
                trucksDAO.freeCache();
                break;
            case "deliveries":
                deliveriesDAO.freeCache();
                break;
            case "shifts":
                shiftsDAO.freeCache();
                break;
            case "workers":
                workersDAO.freeCache();
                break;
        }
    }

    public void deleteDB(){

    }


}
