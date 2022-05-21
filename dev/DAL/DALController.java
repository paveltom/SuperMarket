package DAL;

import DAL.DAObjects.*;
import DAL.DTO.*;
import java.util.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    public boolean updateDriver(DriverDTO driver){
        return driversDAO.updateObj(driver);
    }

    public boolean updateTruck(TruckDTO truck){
        return trucksDAO.updateObj(truck);
    }

    public boolean updateDelivery(DeliveryRecipeDTO deliveryRecipe){
        return deliveriesDAO.updateObj(deliveryRecipe);
    }

    public boolean updateDriverDiary(String key, String shifts){
        return driversDAO.updateDriverDiary(key, shifts);
    }

    public boolean updateTruckDiary(long key, String shifts){
        return trucksDAO.updateTruckDiary(String.valueOf(key), shifts);
    }

     public boolean addDelivery(DeliveryRecipeDTO recipeToAdd){
        return deliveriesDAO.storeObjToDB(recipeToAdd);
     }

    public boolean addTruck(TruckDTO truckToAdd){
        return trucksDAO.storeObjToDB(truckToAdd);
    }

    public boolean addDriver(DriverDTO driverToAdd){
        return driversDAO.storeObjToDB(driverToAdd);
    }

    public boolean removeDelivery(String key){
        String[] keys = {key};
        return deliveriesDAO.deleteObj(keys);
    }

    public boolean removeTruck(long key){
        String[] keys = {String.valueOf(key)};
        return trucksDAO.deleteObj(keys);
    }

    public boolean removeDriver(String key){
        String[] keys = {String.valueOf(key)};
        return driversDAO.deleteObj(keys);
    }

    public boolean addDriverFutureShifts(String key, String toAdd){
        return driversDAO.addDriverFutureShifts(key, toAdd);
    }
    public boolean rewriteDriverFutureShifts(String key, String[] shiftsToAdd){
        return driversDAO.rewriteDriverFutureShifts(key, shiftsToAdd);
    }
    public String getDriverFutureShifts(String key){
        return driversDAO.getDriverFutureShifts(key);
    }


    // ====================================================================================
    // personnel module methods:

    public WorkerDTO getWorker(String _id)
    {
        return (WorkerDTO) workersDAO.getObj(new String[]{_id});
    }
    public List<WorkerDTO> getAllWorkers()
    {
        List<DTO> wList =  workersDAO.getAllObjsFromDB();
        List<WorkerDTO> wDTOList = new ArrayList<WorkerDTO>();
        for (DTO dto:
             wList) {
            wDTOList.add((WorkerDTO) dto);
        }
        return wDTOList;
    }

    public List<WorkerDTO> getWorkerByJob(String _Job)
    {
        List<DTO> wList =  workersDAO.getAllObjByJob(_Job);
        List<WorkerDTO> wDTOList = new ArrayList<WorkerDTO>();
        for (DTO dto:
                wList) {
            wDTOList.add((WorkerDTO) dto);
        }
        return wDTOList;
    }

    public void UpdateWorker(WorkerDTO workerToUpdate)
    {
        workersDAO.updateObj(workerToUpdate);
    }

    public void DeleteWorker(String _id)
    {
        workersDAO.deleteObj(new String[]{_id});
    }
    public void AddWorker(WorkerDTO WorkerToAdd)
    {
        workersDAO.storeObjToDB(WorkerToAdd);
    }

    public List<WorkerDTO> LoadAllWorkers()
    {
        List<DTO> wList =  workersDAO.getAllObjsFromDB();
        List<WorkerDTO> wDTOList = new ArrayList<WorkerDTO>();
        for (DTO dto:
                wList) {
            wDTOList.add((WorkerDTO) dto);
        }
        return wDTOList;
    }

    public HashMap<String,String> getAllAvail()
    {
        return workersDAO.getAllAvail();
    }

    //    public void UpdateShift(String date, String type, String data){

    public void addShift(ShiftDTO shiftToAdd){
        shiftsDAO.storeObjToDB(shiftToAdd);
    }

    public List<ShiftDTO> getShiftHistory(){
        List<ShiftDTO> l = new LinkedList<>();
        for (DTO d:
                shiftsDAO.getAllObjsFromDB()) {
            l.add((ShiftDTO) d);
        }
        return l;
    }

    public ShiftDTO getShift(String date, String type){
        return (ShiftDTO) shiftsDAO.getObj(new String[]{date,type});
    }
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
