package groupprojectoop;

/**
 * Project Name: Car Rental Management System
 * File Name: Vehicle.java
 * 
 * Description:
 * This class defines the Vehicle object with the following properties: vehicle ID, make, model, seating capacity,
 * rental price, availability, and maintenance status. It also includes methods for updating maintenance
 * statuses and managing vehicle availability.
 * 
 * Contributors:
 * - Nick Yi - Designed and implemented the Vehicle class.
 * - Babamayokun Okudero - Enhanced the maintenance status functionality and code review.
 * - Arkadiusz Mercado - Assisted with debugging and optimized methods.
 * 
 * Date Created: 11/30/2024
 * Last Modified: 12/08/2024
 */

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class Vehicle implements Serializable {
    private String vehicleId;
    private String make;
    private String model;
    private int seatingCapacity;
    private double rentalPrice;
    private boolean isAvailable;
    private Map<String, Boolean> maintenanceStatus;

    public Vehicle(String vehicleId, String make, String model, int seatingCapacity, double rentalPrice) {
        this.vehicleId = vehicleId;
        this.make = make;
        this.model = model;
        this.seatingCapacity = seatingCapacity;
        this.rentalPrice = rentalPrice;
        this.isAvailable = true;
        
        this.maintenanceStatus = new LinkedHashMap<>();
        maintenanceStatus.put("Oil", false);
        maintenanceStatus.put("Coolant", false);
        maintenanceStatus.put("Battery", false);
        maintenanceStatus.put("Tires", false);
        maintenanceStatus.put("Wiper Blades", false);
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public double getRentalPrice() {
        return rentalPrice;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Map<String, Boolean> getMaintenanceStatus() {
        return maintenanceStatus;
    }

    public void updateMaintenanceStatus(String component, boolean status) {
        if (maintenanceStatus.containsKey(component)) {
            maintenanceStatus.put(component, status);
            setAvailable(maintenanceStatus.values().stream().allMatch(val -> !val));
        } 
        else {
            System.out.println("Invalid maintenance component: " + component);
        }
    }

    @Override
    public String toString() {
        String status = isAvailable ? "Available" : "Unavailable";
        return String.format("%-10s | %-10s | %-10s | %-5d | $%-8.2f | %s",
            vehicleId, make, model, seatingCapacity, rentalPrice, status);
    }
}

