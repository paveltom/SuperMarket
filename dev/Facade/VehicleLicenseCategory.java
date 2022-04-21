package com.company.Facade;

public enum VehicleLicenseCategory
{

    C(0),  /* No weight restriction  */
    C1(12), /* Up to 12 tone = 12e3 kilograms */
    E(3.5);  /* Above 3.5 tone = 3500 kilograms. adding trailer is allowed.  */

    private double maxWeight;

    VehicleLicenseCategory(double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public double getmaxWeight() {
        return maxWeight;
    }
}
