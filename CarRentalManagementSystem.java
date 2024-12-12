package groupprojectoop;

/**
 * Project Name: Car Rental Management System
 * File Name: CarRentalManagementSystem.java
 * 
 * Description:
 * This class implements the Car Rental Management System, including menus for administrators and customers.
 * Features include adding vehicles, viewing inventory, updating maintenance statuses, booking vehicles,
 * and saving/loading data.
 * 
 * Contributors:
 * - Nick Yi - Design and implementation of the CarRentalManagementSystem class.
 * - Babamayokun Okudero - Designed the booking system and integrated file handling.
 * - Arkadiusz Mercado - Developed the customer menu functionality and handled UI flow.
 * 
 * Date Created: 11/30/2024
 * Last Modified: 12/08/2024
 */

import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.io.*;

public class CarRentalManagementSystem {
    private Map<String, Vehicle> vehicleInventory;
    private final String DATA_FILE = "vehicle_data.bin";
    
    
    // HERE IS THE PASSWORD TO ACCESS ADMIN PRIVILEGES
    private final String ADMIN_PASSWORD = "finalproject";
    // HERE IS THE PASSWORD TO ACCESS ADMIN PRIVILEGES
    
    
    private Scanner scanner;

    public CarRentalManagementSystem() {
        vehicleInventory = new HashMap<>();
        scanner = new Scanner(System.in);
        loadData();
    }

    public static void main(String[] args) {
        CarRentalManagementSystem system = new CarRentalManagementSystem();
        system.mainMenu();
    }

    private void mainMenu() {
        while (true) {
        	System.out.println("==================================================");
            System.out.println("Car Rental Management System:");
            System.out.println("==================================================");
            System.out.println("1. Login as Administrator");
            System.out.println("2. Login as Customer");
            System.out.println("3. Exit");
            System.out.println("==================================================");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> adminLogin();
                case 2 -> customerMenu();
                case 3 -> {
                    saveData();
                    System.out.println("Exiting the system. Goodbye!");
                    return;
                }
                
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void adminLogin() {
    	System.out.println("==================================================");
        System.out.print("Enter Admin Password: ");
        String inputPassword = scanner.nextLine();
        if (inputPassword.equals(ADMIN_PASSWORD)) {
            adminMenu();
        } 
        else {
            System.out.println("\nIncorrect password. Returning to the Main Menu!\n");
        }
    }

    private void adminMenu() {
        while (true) {
        	System.out.println("\n==================================================");
            System.out.println("Administrator Menu:");
            System.out.println("==================================================");
            System.out.println("1. Add Vehicle to Inventory");
            System.out.println("2. View All Vehicles");
            System.out.println("3. Update Vehicle Maintenance Status");
            System.out.println("4. Return a Vehicle");
            System.out.println("5. Delete a Vehicle");
            System.out.println("6. Log Out");
            System.out.println("==================================================");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            
            scanner.nextLine();
            switch (choice) {
                case 1 -> addVehicle();
                case 2 -> viewVehicles();
                case 3 -> updateMaintenanceStatus();
                case 4 -> returnVehicle();
                case 5 -> deleteVehicle();
                case 6 -> {
                    System.out.println("\nLogging out...\n");
                    return;
                }
                
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void customerMenu() {
        while (true) {
            System.out.println("\nCustomer Menu:");
            System.out.println("==============================");
            System.out.println("1. View Available Vehicles");
            System.out.println("2. Search Vehicles by Seating Capacity");
            System.out.println("3. Book a Vehicle");
            System.out.println("4. Return to Main Menu");
            System.out.println("\n==============================");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1 -> searchAvailableVehicles();
                case 2 -> searchVehiclesBySeating();
                case 3 -> bookVehicle();
                case 4 -> {
                    System.out.println("\nReturning to Main Menu...\n");
                    return;
                }
                default -> System.out.println("\nInvalid choice. Please try again.");
            }
        }
    }

    private void bookVehicle() {
        System.out.print("\nEnter the Vehicle ID you want to book: ");
        String vehicleId = scanner.nextLine();
        Vehicle vehicle = vehicleInventory.get(vehicleId);
        
        if (vehicle != null && vehicle.isAvailable()) {
            LocalDate startDate;
            LocalDate endDate;
            
            try {
                System.out.print("Enter Rental Start Date (YYYY-MM-DD): ");
                startDate = LocalDate.parse(scanner.nextLine());
                System.out.print("Enter Rental End Date (YYYY-MM-DD): ");
                endDate = LocalDate.parse(scanner.nextLine());
            } 
            catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Please try again.");
                return;
            }
            System.out.print("Enter your age: ");
           
            int age = scanner.nextInt();
            scanner.nextLine();

            long days = ChronoUnit.DAYS.between(startDate, endDate)+1;
            double baseCost = vehicle.getRentalPrice()*days;
            double ageUpcharge = (age < 21) ? 5.0 * days : 0.0;
            double totalCost = baseCost + ageUpcharge;

            vehicle.setAvailable(false);
            
            System.out.println("\n=============================================================================");
            System.out.printf("Booking confirmed! Total Rental Cost: $%.2f (Base: $%.2f, Age Upcharge (below 21): $%.2f)%n",
                totalCost, baseCost, ageUpcharge);
            System.out.println("=============================================================================");
        } 
        else if (vehicle != null && !vehicle.isAvailable()) {
        	System.out.println("\n===========================================");
            System.out.println("Sorry, this vehicle is currently unavailable.");
        } 
        else {
        	System.out.println("\n===========================================");
            System.out.println("Invalid Vehicle ID.");
        }
    }

    private void viewVehicles() {
        System.out.println("\nAll Vehicles in Inventory:");
        System.out.println("=====================================================================================================================================================");
        System.out.println("Vehicle ID | Make       | Model      | Seats | Price   | Availability | Maintenance");
        System.out.println("=====================================================================================================================================================");
        
        for (Vehicle vehicle : vehicleInventory.values()) {
            String maintenanceSummary = vehicle.getMaintenanceStatus().entrySet().stream()
                .map(entry -> entry.getKey() + ": " + (entry.getValue() ? "Needs Service" : "Good"))
                .reduce((a, b) -> a + ", " + b)
                .orElse("No data");
            System.out.printf("%-10s | %-10s | %-10s | %-5d | $%-8.2f | %-12s | %s%n",
                vehicle.getVehicleId(), vehicle.getMake(), vehicle.getModel(),
                vehicle.getSeatingCapacity(), vehicle.getRentalPrice(),
                vehicle.isAvailable() ? "Available" : "Unavailable", maintenanceSummary);
        }
    }

    private void addVehicle() {
    	System.out.println("\n==================================================");
        System.out.println("Enter Vehicle Details:");
        System.out.print("Vehicle ID (MUST BE UNIQUE): ");
        String vehicleId = scanner.nextLine();

        if (vehicleInventory.containsKey(vehicleId)) {
            System.out.println("Error: Vehicle ID already exists. Please try again with a unique ID.");
            return;
        }

        System.out.print("Make: ");
        String make = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("Seating Capacity: ");
        int seatingCapacity = scanner.nextInt();
        System.out.print("Rental Price per Day: ");
        double rentalPrice = scanner.nextDouble();
        scanner.nextLine();
        
        Vehicle newVehicle = new Vehicle(vehicleId, make, model, seatingCapacity, rentalPrice);
        vehicleInventory.put(vehicleId, newVehicle);
        System.out.println("\n--> Vehicle added successfully! <--");
    }
    
    private void updateMaintenanceStatus() {
    	System.out.println("\n==================================================");
        System.out.print("Enter Vehicle ID to update maintenance status: ");
        String vehicleId = scanner.nextLine();
        Vehicle vehicle = vehicleInventory.get(vehicleId);
        
        if (vehicle != null) {
            System.out.println("\nChoose a maintenance component to update:");
            System.out.println("1. Oil\n2. Coolant\n3. Tires\n4. Wiper Blades\n5. Battery");
            System.out.println("\n==================================================");
            System.out.print("Enter your choice (1-5): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            String component = switch (choice) {
                case 1 -> "Oil";
                case 2 -> "Coolant";
                case 3 -> "Tires";
                case 4 -> "Wiper Blades";
                case 5 -> "Battery";
                default -> null;
            };

            if (component != null) {
                System.out.print("Mark as (1) Needs Service or (2) Good Condition: ");
                
                int status = scanner.nextInt();
                scanner.nextLine();
                vehicle.updateMaintenanceStatus(component, status == 1);
                
                System.out.println("\n= = = = = = = = = = = = = = = = = = = = = = = = = =");
                System.out.println("Maintenance Status Updated for " + component);
            } 
            else {
                System.out.println("Invalid component choice.");
            }
        } 
        else {
            System.out.println("Vehicle not found.");
        }
    }

    private void returnVehicle() {
    	System.out.println("\n==================================================");
        System.out.print("Enter Vehicle ID to return: ");
        String vehicleId = scanner.nextLine();
        Vehicle vehicle = vehicleInventory.get(vehicleId);
        
        if (vehicle != null) {
            vehicle.setAvailable(true);
            System.out.println("\n= = = = = = = = = = = = = = = = = = = = = = = = = =");
            System.out.println("Vehicle has been returned and is now available.");
        } 
        else {
        	System.out.println("\n= = = = = = = = = = = = = = = = = = = = = = = = = =");
            System.out.println("Vehicle not found.");
        }
    }

    private void deleteVehicle() {
    	System.out.println("\n==================================================");
        System.out.print("Enter Vehicle ID to delete: ");
        String vehicleId = scanner.nextLine();
        
        if (vehicleInventory.remove(vehicleId) != null) {
        	System.out.println("\n= = = = = = = = = = = = = = = = = = = = = = = = = =");
            System.out.println("Vehicle deleted successfully.");
        } 
        else {
        	System.out.println("\n= = = = = = = = = = = = = = = = = = = = = = = = = =");
            System.out.println("Vehicle not found.");
        }
    }

    private void searchAvailableVehicles() {
        System.out.println("\nAvailable Vehicles:");
        System.out.println("======================================================================");
        System.out.println("Vehicle ID | Make       | Model      | Seats | Price   | Availability");
        System.out.println("======================================================================");
        
        for (Vehicle vehicle : vehicleInventory.values()) {
            if (vehicle.isAvailable()) {
                System.out.println(vehicle);
            }
        }
    }
    
    private void searchVehiclesBySeating() {
        System.out.print("\nEnter the minimum seating capacity you require: ");
        int minSeats = scanner.nextInt();
        scanner.nextLine();
        
        System.out.println("\nAvailable Vehicles with Seating Capacity of " + minSeats + " or more:");
        System.out.println("======================================================================");
        System.out.println("Vehicle ID | Make       | Model      | Seats | Price   | Availability");
        System.out.println("======================================================================");

        for (Vehicle vehicle : vehicleInventory.values()) {
            if (vehicle.isAvailable() && vehicle.getSeatingCapacity() >= minSeats) {
                System.out.println(vehicle);
            }
        }
    }
    
    private void loadData() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            vehicleInventory = (Map<String, Vehicle>) inputStream.readObject();
        } 
        catch (FileNotFoundException e) {
            System.out.println("No previously saved data.");
        } 
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveData() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            outputStream.writeObject(vehicleInventory);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

