# **Car Rental Management System**

## **Overview**
The Car Rental Management System is a Java-based application designed for efficient management of rental vehicles. It supports both **administrators** and **customers** with the following features: vehicle inventory management, maintenance tracking, and rental booking.

This project emphasizes Object Oriented Programming principles, data persistence, and user-friendly design.

---

### **Administrator Features**
- **Add a Vehicle**: Add new vehicles to the system with: ID, make, model, seating capacity, and rental price.
- **View Inventory**: Display all vehicles with their availability and maintenance status.
- **Update Maintenance Status**: Mark components (oil, tires, battery, etc.) as "Needs Service" or "Good Condition." Vehicles needing service are marked unavailable.
- **Delete Vehicles**: Remove vehicles from the system.
- **Return Vehicles**: Mark a booked vehicle as returned, updating its availability.
- **Data Persistence**: Saves and loads inventory data using binary file storage.

### **Customer Features**
- **View Available Vehicles**: View vehicles marked as "Available" with relevant details.
- **Search Vehicles based on Seating Capacity**: Choose to search for vehicles based on the required seating capacity. 
- **Book a Vehicle**: Reserve vehicles by specifying rental dates. Under 21 customers incur an additional fee.
- **User-Friendly Menus**: Easy-to-navigate UI for customer actions.

---

## **Administrator Login**
- **Password**: "finalproject"
- Use this to access admin-specific features: adding, deleting, and managing vehicle maintenance.

---

## **Technical Details**

### **Core Classes**
- **Vehicle**: Represents individual vehicles with attributes of ID, make, model, and maintenance status. Methods include updating maintenance and checking availability.
- **CarRentalManagementSystem**: Manages application flow, user menus, input handling, and data storage.

### **Data Persistence**
- Inventory data is saved in "vehicle_data.bin" using Java's "ObjectOutputStream" and reloaded using "ObjectInputStream".

### **Exception Handling**
- Handles invalid inputs, including:
  - Non-numeric menu selections.
  - Invalid date formats.
  - Attempts to access unavailable vehicles.

---

## **Team Members**
- **Nick Yi**
- **Babamayokun Okudero**
- **Arkadiuz Mercado**
