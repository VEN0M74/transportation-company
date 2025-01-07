import java.util.*;
import java.io.*;
public class Driver extends Employee{
    private List<String> assignedTrips;
    private String name;
    private int driverid;
    private Vehicle vehicle;


    public Driver(String username, String password, String id) {
        super(username, password, id);
        this.assignedTrips = new ArrayList<>();
    }


    public Driver(String username, String password, String id, String name, String driverId) {
        super(username, password, id);
        this.name = name;
        this.driverid = driverid;
        this.assignedTrips = new ArrayList<>();
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDriverId(int driverid) {
        this.driverid = driverid;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public String getName() {
        return name;
    }

    public int getDriverId() {
        return driverid;
    }


}
