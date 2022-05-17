package DAL;

import DAL.DAObjects.*;
import DAL.DTO.*;

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
        return null;
    }

    public List<TruckDTO> getAllTrucks(){
        return null;
    }

    public List<DeliveryRecipeDTO> getAllDeliveries(){
        return null;
    }

    public DriverDTO getDriver(String key){
        return null;
    }

    public TruckDTO getTruck(String key){
        return null;
    }

    public DeliveryRecipeDTO getDelivery(String key){
        return null;
    }

    public void updateDriverDiary(String key, String shifts){
    }

    public void updateTruckDiary(String key, String shifts){
    }

     public void addDelivery(DeliveryRecipeDTO recipeToAdd){
     }

    public void addTruck(TruckDTO truckToAdd){
    }

    public void addDriver(DriverDTO driverToAdd){

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


    public void UpdateShift(String date, String type, String data){
        shiftsDAO.updateShift(date,type,data);
    }

    public List<ShiftDTO> getShiftHistory(){
        return shiftsDAO.getShiftHistory();
    }

    public ShiftDTO getShift(String date, String type){
        return shiftsDAO.getShift(date,type);
    }

    public void updateAvailability(String id, String availability){
        shiftsDAO.updateAvailability(id,availability);
    }

    public List<String> getAllAvailability(){
        return workersDAO.getAllAvailability();
    }

    public String getAvailability(String id){
        return shiftsDAO.getAvailability(id);
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
