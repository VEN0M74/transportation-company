import java.util.*;
import java.io.*;
import java.util.concurrent.ThreadLocalRandom;
public class Trip {
    private String type;
    private String date;
    private String time;
    private double price;
    private String source;
    private String destination;
    private int SelectTrip;
    private int numStops;
    private int availableSeats;
    private String TripId;
    private List<Trip> trips;
    private Vehicle vehicle;
    private String driverId;
    private String name;
    public Trip() {
    }
    public Trip(double price, String source , String destination , String type, int numStops , Vehicle vehicle) {
        this.price = price;
        this.source=source;
        this.destination=destination;
        this.type=type;
        this.numStops=numStops;
        this.vehicle=vehicle;
        this.TripId=generateTripID();

    }
    public Trip(int availableSeats, String date, int numStops, int selectTrip, double price, String time, String type, String source, String destination, String TripId) {
        this.availableSeats = availableSeats;
        this.date = date;
        this.numStops = numStops;
        this.SelectTrip = SelectTrip;
        this.price = price;
        this.time = time;
        this.type = type;
        this.source = source;
        this.destination = destination;
        this.TripId = TripId;
        this.trips = new ArrayList<>();

    }
    public int getAvailableSeats() {
        return availableSeats;
    }
    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getDestination() {
        return destination;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getNumStops() {
        return numStops;
    }

    public void setNumStops(int numStops) {
        this.numStops = numStops;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSelectTrip() {
        return SelectTrip;
    }

    public void setSelectTrip(int selectTrip) {
        SelectTrip = selectTrip;
    }
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTripId() {
        return TripId;
    }
    public void addTrip(String source, String destination, String type, int numberOfStops) {
        // Logic to add trip details
        System.out.println("Trip added: " + source + " to " + destination + ", Type: " + type + ", Stops: " + numberOfStops);
    }

    public void setTripId(String tripId) {
        TripId = tripId;
    }
    public static String generateTripID() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int digit = ThreadLocalRandom.current().nextInt(1, 10);
            sb.append(digit);
        }
        return sb.toString();
    }

    public void decreaseAvailableSeats() {
        if (availableSeats > 0) {
            availableSeats--;
        }
    }
    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public String getTripDetails() {
        return TripId + ":" + source + ":" + destination + ":" + type + ":" + price + ":" + numStops + ":" + vehicle.getType() + ":" + availableSeats + ":" + name + ":" + driverId;
    }
}

