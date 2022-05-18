package DAL;

import DAL.DAObjects.*;
import DAL.DTO.*;
import DeliveryModule.BusinessLayer.Element.DeliveryRecipe;

import java.util.ArrayList;
import java.util.List;

public class DALController {

    private DriversDAO driversDAO;
    private TrucksDAO trucksDAO;
    private DeliveriesDAO deliveriesDAO;
    //private WorkersDAO workersDAO;
    //private ShiftsDAO shiftsDAO;

    private static class DALControllerHolder {
        private static DALController instance = new DALController();
    }

    private DALController(){
        driversDAO = new DriversDAO();
        trucksDAO = new TrucksDAO();
        deliveriesDAO = new DeliveriesDAO();
        //workersDAO = new WorkersDAO();
        //shiftsDAO = new ShiftsDAO();
    }

    public static DALController getInstance(){
        return DALControllerHolder.instance;
    }

    // delivery module methods:

    public List<DriverDTO> getAllDrivers(){
        List<DTO> res = driversDAO.getAllObjsFromDB();
        List<DriverDTO> out = new ArrayList<>();
        for(DTO temp : res){
            out.add((DriverDTO) temp);
        }
        return out;
    }

    public List<TruckDTO> getAllTrucks(){
        List<DTO> res = trucksDAO.getAllObjsFromDB();
        List<TruckDTO> out = new ArrayList<>();
        for(DTO temp : res){
            out.add((TruckDTO) temp);
        }
        return out;
    }

    public List<DeliveryRecipeDTO> getAllDeliveries(){
        List<DTO> res = deliveriesDAO.getAllObjsFromDB();
        List<DeliveryRecipeDTO> out = new ArrayList<>();
        for(DTO temp : res){
            out.add((DeliveryRecipeDTO) temp);
        }
        return out;
    }

    public DriverDTO getDriver(String key){
        String[] keys = {key};
        return (DriverDTO)driversDAO.getObj(keys);
    }

    public TruckDTO getTruck(String key){
        String[] keys = {key};
        return (TruckDTO) trucksDAO.getObj(keys);
    }

    public DeliveryRecipeDTO getDelivery(String key){
        String[] keys = {key};
        return (DeliveryRecipeDTO) deliveriesDAO.getObj(keys);
    }

    public void updateDriverDiary(String key, String shifts){
        driversDAO.updateDriverDiary(key, shifts);
    }

    public void updateTruckDiary(long key, String shifts){
        trucksDAO.updateTruckDiary(String.valueOf(key), shifts);
    }

     public void addDelivery(DeliveryRecipeDTO recipeToAdd){
        deliveriesDAO.storeObjToDB(recipeToAdd);
     }

    public void addTruck(TruckDTO truckToAdd){
        trucksDAO.storeObjToDB(truckToAdd);
    }

    public void addDriver(DriverDTO driverToAdd){
        driversDAO.storeObjToDB(driverToAdd);
    }

    public void removeDelivery(String key){
        String[] keys = {key};
        deliveriesDAO.deleteObj(keys);
    }

    public void removeTruck(long key){
        String[] keys = {String.valueOf(key)};
        deliveriesDAO.deleteObj(keys);
    }

    public void removeDriver(String key){
        String[] keys = {String.valueOf(key)};
        deliveriesDAO.deleteObj(keys);
    }

    public void addDriverFutureShifts(String key, String toAdd){
        driversDAO.addDriverFutureShifts(key, toAdd);
    }
    public void rewriteDriverFutureShifts(String key, String[] shiftsToAdd){
        driversDAO.rewriteDriverFutureShifts(key, shiftsToAdd);
    }
    public String getDriverFutureShifts(String key){
        return driversDAO.getDriverFutureShifts(key);
    }


    // ====================================================================================
    // personnel module methods:

//    public WorkerDTO getWorker(String _id)
//    {
//        return workersDAO.getWorker(_id);
//    }
//    public List<WorkerDTO> getAllWorkers()
//    {
//        return workersDAO.getAllWorkers();
//    }
//
//    public List<WorkerDTO> getWorkerByJob(String _Job)
//    {
//        return workersDAO.getWorkerByJob(_Job);
//    }
//
//    public void UpdateWorker(String _id,String field,String _data)
//    {
//        workersDAO.UpdateWorker(_id,field,_data);
//    }
//
//    public void DeleteWorker(String _id)
//    {
//        workersDAO.DeleteWorker(_id);
//    }
//    public void DeleteDriver(String _id)
//    {
//        driversDAO.DeleteDriver(_id);
//    }
//
//
//    public void UpdateShift(String date, String type, String data){
//        shiftsDAO.updateShift(date,type,data);
//    }
//
//    public List<ShiftDTO> getShiftHistory(){
//        return shiftsDAO.getShiftHistory();
//    }
//
//    public ShiftDTO getShift(String date, String type){
//        return shiftsDAO.getShift(date,type);
//    }
//
//    public void updateAvailability(String id, String availability){
//        shiftsDAO.updateAvailability(id,availability);
//    }
//
//    public List<String> getAllAvailability(){
//        return workersDAO.getAllAvailability();
//    }
//
//    public String getAvailability(String id){
//        return shiftsDAO.getAvailability(id);
//    }
//
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
//            case "shifts":
//                shiftsDAO.freeCache();
//                break;
//            case "workers":
//                workersDAO.freeCache();
//                break;
        }
    }

    public void deleteDB(){

    }


}
