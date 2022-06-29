package DeliveryModule.Facade.FacadeObjects;


import DeliveryModule.BusinessLayer.Element.Truck;

public class FacadeTruck {
    private String licensePlate;
    private String model, parkingArea;
    private double netWeight, maxLoadWeight;

    public  FacadeTruck(){}

    public FacadeTruck(Truck truck){
        this.licensePlate = truck.VehicleLicenseNumber;
        this.model = truck.Model;
        this.netWeight = truck.NetWeight;
        this.maxLoadWeight = truck.MaxLoadWeight;
        this.parkingArea = truck.Zone.name();
    }

    public FacadeTruck(String licensePlate, String model, String parkingArea, double netWeight, double maxLoadWeight){
        this.licensePlate = licensePlate;
        this.model = model;
        this.parkingArea = parkingArea;
        this.netWeight = netWeight;
        this.maxLoadWeight = maxLoadWeight;
    }

    public String toString(){
        return licensePlate + ", " + model + ", " + parkingArea + ", net weight - " + netWeight + ", max load weight - " + maxLoadWeight;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getModel() {
        return model;
    }

    public String getParkingArea() {
        return parkingArea;
    }

    public double getNetWeight() {
        return netWeight;
    }

    public double getMaxLoadWeight() {
        return maxLoadWeight;
    }
}
