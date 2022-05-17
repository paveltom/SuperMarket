package DAL;

import DAL.DAObjects.*;

public class DALController {

    private DriversDAO driversDAO;

    private TrucksDAO trucksDAO;

    private DeliveriesDAO deliveriesDAO;

    private WorkersDAO workerDAO;

    private ShiftsDAO shiftsDAO;

    private static class DALControllerHolder {
        private static DALController instance = new DALController();
    }

    private DALController(){
        driversDAO = new DriversDAO();
        trucksDAO = new TrucksDAO();
        deliveriesDAO = new DeliveriesDAO();
        workerDAO = new WorkersDAO();
        shiftsDAO = new ShiftsDAO();
    }

    public static DALController getInstance(){
        return DALControllerHolder.instance;
    }

    // delivery module methods:

    // lookup the diagram - is that enough? other needed?

    // getAllDrivers(): List<DriverDTO>

    // getAllTrucks(): List<TruckDTO>

    // getAllDeliveries(): List<DeliveriesDTO>

    // getDriver(key): DriverDTO

    // getTruck(key): TruckDTO

    // getDelivery(key): DeliveryDTO

    // updateDriverDiary(key, shifts: string)

    // updateTruckDiary(key, shifts; string)

    // addDelivery(DeliveryDTO{supplier, client, orderID,  products, subDate})

    // addTruck(TruckDTO)

    // removeDelivery(DeliveryDTO)

    // removeTruck(TruckDTO)

    // clearCache()

    // deleteDB()





    // ==========================================================
    // personnel module methods:

    public WorkerDTO getWorker(String _id)
    {
        return workerDao.getWorker(_id);
    }
    public List<WorkerDTO> getAllWorkers()
    {
        return workerDao.getAllWorkers();
    }

    public List<WorkerDTO> getWorkerByJob(String _Job)
    {
        return WorkersDAO.getWorkerByJob(_Job);
    }

    public void UpdateWorker(String _id,String field,String _data)
    {
        WorkersDAO.UpdateWorker(_id,field,_data);
    }

    public void DeleteWorker(String _id)
    {
        workerDao.DeleteWorker(_id);
    }
    public void DeleteDriver(String _id)
    {
        driversDAO.DeleteDriver(_id);
    }


}
