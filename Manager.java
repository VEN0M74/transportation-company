import java.util.*;
import java.io.*;
public class Manager extends Employee {

    String name = "Manager";
    private List<Trip> assignedTrips;
    private List<Vehicle> managedVehicles;
    private List<Employee> managedEmployees;
    private List<Driver> managedDrivers;


    public Manager(String username, String password, String id) {
        super(username, password, id);
    }

    public String getName() {
        return name;
    }

    public Manager(String name, String UserName, String password, String ID) {
        super(UserName, password, ID);
        this.assignedTrips = assignedTrips;
        this.managedVehicles = managedVehicles;
        this.managedEmployees = managedEmployees;
        this.managedDrivers = managedDrivers;
    }

    public void setManagedDrivers(List<Driver> managedDrivers) {
        this.managedDrivers = managedDrivers;
    }
    public static void viewReports() {
        System.out.println("Displaying trip details:");
        try (BufferedReader reader = new BufferedReader(new FileReader("trip details.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length >= 6) { // Check if parts array has at least 6 elements
                    System.out.println("Driver Name: " + parts[8]);
                    System.out.println("Trip ID: " + parts[0]);
                    System.out.println("Source: " + parts[1]);
                    System.out.println("Destination: " + parts[2]);
                    System.out.println("Type: " + parts[3]);
                    System.out.println("Price: " + parts[4]);
                    System.out.println("Number of stops: " + parts[5]);
                    System.out.println("Vehicle type: " + parts[6]);
                    System.out.println("Available Seats :"+parts[7]);
                    System.out.println("----------------------------------");
                } else {
                    System.out.println("Invalid trip details format: " + line);}}
        } catch (IOException e) {
            System.out.println("Error reading trip details from file: " + e.getMessage());}
    }
}
//    public List<Driver> getManagedDrivers() {
//        return managedDrivers;
//    }
//    public void addTrip(Trip trip) {
//        assignedTrips.add(trip);
//    }
//    public void removeTrip(Trip trip) {
//        assignedTrips.remove(trip);
//    }
//    public void addVehicle(Vehicle vehicle) {
//        managedVehicles.add(vehicle);
//    }
//    public void removeVehicle(Vehicle vehicle) {
//        managedVehicles.remove(vehicle);
//    }
//    public void addEmployee(Employee employee) {
//        managedEmployees.add(employee);
//    }
//    public void removeEmployee(Employee employee) {
//        managedEmployees.remove(employee);
//    }
//    public void manageTrip(String action, Trip trip) {
//    }
//    public void setTripDetails(Trip trip, String date, String source, String destination, String type) {
//        trip.setDate(date);
//        trip.setSource(source);
//        trip.setDestination(destination);
//        trip.setType(type);
//        System.out.println("Trip details updated successfully!");
//    }
//}
