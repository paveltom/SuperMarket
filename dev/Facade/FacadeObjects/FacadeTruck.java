package Facade.FacadeObjects;

public class FacadeTruck {
    private int licensePlate;
    private String model, parkingArea;
    private double netWeight, maxLoadWeight;

    public  FacadeTruck(){}

    public FacadeTruck(int licensePlate, String model, String parkingArea, double netWeight, double maxLoadWeight){
        this.licensePlate = licensePlate;
        this.model = model;
        this.parkingArea = parkingArea;
        this.netWeight = netWeight;
        this.maxLoadWeight = maxLoadWeight;
    }

    public int getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(int licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getParkingArea() {
        return parkingArea;
    }

    public void setParkingArea(String parkingArea) {
        this.parkingArea = parkingArea;
    }

    public double getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(double netWeight) {
        this.netWeight = netWeight;
    }

    public double getMaxLoadWeight() {
        return maxLoadWeight;
    }

    public void setMaxLoadWeight(double maxLoadWeight) {
        this.maxLoadWeight = maxLoadWeight;
    }
}
