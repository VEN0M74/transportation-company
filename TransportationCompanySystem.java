import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class TransportationCompanySystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
       // new MainFrame();
        System.out.println("Welcome to the Transportation System!");
        while (true) {
            System.out.println("\n1. Register\n2. Login\n3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    registerUser(scanner);
                    break;
                case 2:
                    loginUser(scanner);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
    }
    public static void registerUser(Scanner scanner) {
        System.out.println("Registration:");
        System.out.println("1. Passenger");
        System.out.println("2. Employee");
        System.out.print("Choose user type: ");
        int userTypeChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        String userType;
        if (userTypeChoice == 1) {
            userType = "Passenger";
        } else if (userTypeChoice == 2) {
            System.out.println("1. Manager");
            System.out.println("2. Driver");
            System.out.print("Choose employee type: ");
            int employeeTypeChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            //ternary if
            userType = (employeeTypeChoice == 1) ? "Manager" : "Driver";
        } else {
            System.out.println("Invalid choice. Registration failed.");
            return;
        }


        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        // Generate ID based on user type
        String id;
        if (userType.equals("Passenger")) {
            // p + 4 digits
            id = "P" + String.format("%04d", generateRandomId());
            Passenger passenger = new Passenger(username, password, id);
            savePassengerInfo(passenger);
        } else { // Employee
            id = "E" + String.format("%04d", generateRandomId());
            Employee employee;
            if (userType.equals("Manager")) {
                employee = new Manager(username, password, id);
                saveEmployeeInfo(employee, "manager.txt");
            } else { // Driver
                employee = new Driver(username, password, id);
                saveEmployeeInfo(employee, "Driver.txt");
            }
        }


        System.out.println("Registration successful! Your ID is: " + id);}
    public  static void savePassengerInfo(Passenger passenger) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("passenger_credentials.txt", true))) {
            writer.println(passenger.getUsername() + ":" + passenger.getPassword() + ":" + passenger.getId());
        } catch (IOException e) {
            System.out.println("Error writing to passenger file: " + e.getMessage());}}
    public static void saveEmployeeInfo(Employee employee, String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            String userType;
            if (employee instanceof Manager) {
                userType = "Manager";
            } else if (employee instanceof Driver) {
                userType = "Driver";
            } else {
                System.out.println("Invalid employee type.");
                return;}
            writer.println(employee.getUsername() + ":" + employee.getPassword() + ":" + employee.getId() + ":" + userType); // Include userType
        } catch (IOException e) {
            System.out.println("Error writing to employee file: " + e.getMessage());}}
    public static void loginUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();


        if (checkPassengerCredentials(username, password)) {
            System.out.println("Login successful! Passenger logged in.");
            String[] userInfo = getUserInfo("passenger_credentials.txt", username);
            if (userInfo != null) {
                System.out.println("Choose an option:");
                System.out.println("1. Book a new ticket");
                System.out.println("2. View existing bookings");
                System.out.println("3. Delete existing Trip ");
                System.out.print("Enter your choice : ");
                String choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        displayAvailableTrips(scanner, userInfo[0]); // userInfo[0] contains the username
                        break;
                    case "2":
                        List<String> passengerBookings = getPassengerBookings(username, "passtrips.txt");
                        if (!passengerBookings.isEmpty()) {
                            System.out.println("Passenger's bookings:");
                            for (String booking : passengerBookings) {
                                System.out.println(booking);}
                        } else {
                            System.out.println("No bookings found for passenger.");}
                        break;
                    case "3":
                        deleteTrip(scanner, username);
                        break;
                    default:
                        System.out.println("Invalid choice. Exiting...");
                }
            }
        } else if (checkManagerCredentials(username, password)) {
            System.out.println("Login successful! Manager logged in.");
            String[] userInfo = getUserInfo("manager.txt", username);
            if (userInfo != null) {
                displayManagerActions(scanner);
            }


        } else if (checkDriverCredentials(username, password)) {
            System.out.println("Login successful! Driver logged in.");
            String[] userInfo = getUserInfo("Driver.txt", username);
            if (userInfo != null) {
                List<String> assignedTripDetails = getAssignedTripDetails(username);
                if (!assignedTripDetails.isEmpty()) {
                    System.out.println("Assigned Trip Details:");
                    for (String tripDetails : assignedTripDetails) {
                        System.out.println(tripDetails);}
                } else {
                    System.out.println("No trips assigned to you.");}}
        } else {
            System.out.println("Invalid username or password. Login failed.");}
    }
    public static boolean checkPassengerCredentials(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("passenger_credentials.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 3 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;}}
        } catch (IOException e) {
            System.out.println("Error reading passenger credentials: " + e.getMessage());}
        return false;
    }


    public static boolean checkManagerCredentials(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("manager.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 4 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;}}
        } catch (IOException e) {
            System.out.println("Error reading manager credentials: " + e.getMessage());}
        return false;
    }

    public static boolean checkDriverCredentials(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Driver.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 4 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;}}
        } catch (IOException e) {
            System.out.println("Error reading driver credentials: " + e.getMessage());
        }
        return false;
    }
    public static String[] getUserInfo(String filename, String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length >= 3 && parts[0].equals(username)) {
                    return parts; // Return username, password, and userType
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading credentials: " + e.getMessage());}
        return null;
    }


    public static void displayAvailableTrips(Scanner scanner, String userId) {
        List<Trip> availableTrips = new ArrayList<>();
        // Read trip details from the file and populate the availableTrips list
        try (BufferedReader reader = new BufferedReader(new FileReader("trip details.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Remove the prefix "Trip : "
                line = line.replace("Trip : ", "");
                String[] parts = line.split(":");
                if (parts.length >= 9) { // Adjust the length check according to your data
                    String tripId = parts[0];
                    String source = parts[1];
                    String destination = parts[2];
                    String type = parts[3];
                    double price = Double.parseDouble(parts[4]);
                    int numberOfStops = Integer.parseInt(parts[5]);
                    String vehicleType = parts[6];
                    int availableSeats = Integer.parseInt(parts[7]);
                    Vehicle vehicle;
                    switch (vehicleType) {
                        case "Limousine":
                            vehicle = Vehicle.LIMOUSINE;
                            break;
                        case "Bus":
                            vehicle = Vehicle.BUS;
                            break;
                        case "Minibus":
                            vehicle = Vehicle.MINIBUS;
                            break;
                        default:
                            vehicle = null;
                            break;
                    }
                    Trip trip = new Trip(price, source, destination, type, numberOfStops, vehicle);
                    trip.setTripId(tripId);
                    trip.setAvailableSeats(availableSeats);
                    availableTrips.add(trip);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading trip details from file: " + e.getMessage());
        }

        // Display available trips and prompt the user to choose one
        System.out.println("Available Trips:");
        for (int i = 0; i < availableTrips.size(); i++) {
            Trip trip = availableTrips.get(i);
            System.out.println((i + 1) + ". Trip ID: " + trip.getTripId() +
                    ", Source: " + trip.getSource() +
                    ", Destination: " + trip.getDestination() +
                    ", Type: " + trip.getType() +
                    ", Price: " + trip.getPrice() +
                    ", Available Seats: " + trip.getAvailableSeats());}
        System.out.print("Enter the number of the trip you want to book (or 0 to cancel): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline


        if (choice >= 1 && choice <= availableTrips.size()) {
            Trip selectedTrip = availableTrips.get(choice - 1);
            bookTrip(selectedTrip, userId,scanner);
        } else {
            System.out.println("Invalid choice. Booking canceled.");}
    }
    public static void bookTrip(Trip trip, String userId, Scanner scanner) {
        if (trip.getAvailableSeats() == 0) {
            System.out.println("Sorry, there are no available seats for this trip.");
            return;
        }
        // Decrease the available seats for the selected trip
        trip.decreaseAvailableSeats();
        // Update the trip details file with the decreased available seats
        updateTripDetailsFile(trip);
        // Optionally, you can save the booking information for the user
        saveTripInfo(trip, userId);
        System.out.println("Trip booked successfully!");
        // Ask if the user wants to book another ticket
        System.out.print("Do you want to book another ticket? (yes/no): ");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("yes")) {
            displayAvailableTrips(scanner, userId);
        } else {
            System.out.println("Exiting booking...");
            scanner.close();
            System.exit(0);}}
    public static void updateTripDetailsFile(Trip trip) {
        // Define the file path where trip details are stored
        String filePath ="trip details.txt";
        try {
            // Read the contents of the file
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            boolean tripUpdated = false;
            // Iterate through each line in the file
            while ((line = reader.readLine()) != null) {
                // Split the line into trip details using the delimiter
                String[] tripDetails = line.split(":");
                // Check if the current line corresponds to the trip to be updated
                if (tripDetails.length > 0 && tripDetails[0].equals(trip.getTripId())) {
                    // Decrease the available seats for this trip
                    int availableSeats = Integer.parseInt(tripDetails[7]);
                    availableSeats--;
                    tripDetails[7] = String.valueOf(availableSeats);
                    tripUpdated = true;}
                // Reconstruct the line with updated trip details
                stringBuilder.append(String.join(":", tripDetails)).append("\n");}
            reader.close();
            // Write the updated trip details back to the file
            FileWriter writer = new FileWriter(file);
            writer.write(stringBuilder.toString());
            writer.close();
            if (!tripUpdated) {
                System.out.println("Trip with ID " + trip.getTripId() + " not found in the file.");}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveTripInfo(Trip trip, String userId) {
        String fileName = "passtrips.txt";
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
            writer.println(userId + ":" + trip.getTripId() + ":" + currentDate + ":" + trip.getSource() + ":" + trip.getDestination() + ":" + trip.getType());
            System.out.println("Trip details saved successfully!");
        } catch (IOException e) {
            System.out.println("Error writing to user trip file: " + e.getMessage());}}


    public static void deleteTrip(Scanner scanner, String username) {
        System.out.print("Enter the trip ID you want to delete: ");
        String tripIdToDelete = scanner.nextLine();
        String filePath = "passtrips.txt";
        List<String> updatedPassengerBookings = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length >= 2 && parts[0].equals(username) && parts[1].equals(tripIdToDelete)) {
                    // Trip to be deleted found, skip it
                    continue;}
                updatedPassengerBookings.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading passenger bookings file: " + e.getMessage());
            return;}
        // Write the updated bookings back to the file
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (String booking : updatedPassengerBookings) {
                writer.println(booking);}
            System.out.println("Trip deleted successfully!");
        } catch (IOException e) {
            System.out.println("Error writing updated passenger bookings file: " + e.getMessage());}
    }


    public static List<String> getPassengerBookings(String username, String filePath) {
        List<String> passengerBookings = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length >= 5 && parts[0].equals(username)) {
                    String id = parts[1];
                    String date=parts[2];
                    String source = parts[3];
                    String destination = parts[4];
                    String type = parts[5];
                    System.out.println("Trip ID: " + id);
                    System.out.println("Date : " + date);
                    System.out.println("Source: " + source);
                    System.out.println("Destination: " + destination);
                    System.out.println("Type of trip: " + type);
                    System.out.println("--------------------");
                    passengerBookings.add(line);}
            }

        } catch (IOException e) {
            System.out.println("Error reading passenger credentials file: " + e.getMessage());}
        return passengerBookings;
    }

    public static void displayManagerActions(Scanner scanner) {
        System.out.println("Welcome, Manager!");
        System.out.println("Actions available:");
        System.out.println("1. Manage trips");
        System.out.println("2. Display Report");
        System.out.println("3. Logout");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        switch (choice) {
            case 1:
                manageTrips(scanner);
                break;
            case 2:
                Manager.viewReports();
                break;
            case 3:
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    public static void manageTrips(Scanner scanner) {
        System.out.println("Welcome, Manager! What would you like to do?");
        System.out.println("1. Add trip details");
        System.out.println("2. Delete trip details");
        System.out.println("3. Logout");

        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                Trip tripToAdd = addTripDetails(scanner);
                if (tripToAdd != null)
                    saveTripDetails(tripToAdd);

                break;
            case 2:
                deleteTripDetails(scanner);
                break;
            case 3:
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    public static Trip addTripDetails(Scanner scanner) {
        System.out.println("Enter trip details:");
        System.out.print("Source: ");
        String source = scanner.nextLine();
        System.out.print("Destination: ");
        String destination = scanner.nextLine();
        System.out.print("Type (One-way/Round-trip): ");
        String type = scanner.nextLine();
        System.out.print("Price: ");
        Double price = scanner.nextDouble();
        System.out.print("Number of stops: ");
        int numberOfStops = scanner.nextInt();
        System.out.println("Select vehicle type:");
        System.out.println("1. Limousine");
        System.out.println("2. Bus");
        System.out.println("3. Minibus");
        System.out.print("Enter your choice: ");
        int vehicleChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        Vehicle vehicle;
        switch (vehicleChoice) {
            case 1:
                vehicle = Vehicle.LIMOUSINE;
                break;
            case 2:
                vehicle = Vehicle.BUS;
                break;
            case 3:
                vehicle = Vehicle.MINIBUS;
                break;
            default:
                System.out.println("Invalid choice. Defaulting to Limousine.");
                vehicle = Vehicle.LIMOUSINE;}
        System.out.print("Number of available seats: ");
        int availableSeats = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        String driverUsername;
        String driverId = null; // Check if the driver is registered and get their ID from the driver text file

        do {
            System.out.print("Enter driver's username: ");
            driverUsername = scanner.nextLine().trim(); // Trim leading and trailing whitespaces
            // Check if the driver is registered and get their ID from the driver text file
            driverId = getDriverId(driverUsername);
            if (driverId == null) {
                System.out.println("Driver not found. Please enter a valid driver.");}
        } while (driverId == null);
        Trip trip = new Trip(price, source, destination, type, numberOfStops, vehicle);
        trip.setAvailableSeats(availableSeats);
        trip.setDriverId(driverId);
        trip.setName(driverUsername);// Save the trip details
        System.out.println("Saving trip details: " + trip.getTripDetails());
        saveTripDetails(trip);
        System.out.println("Trip details saved.");
        System.out.print("Do you want to add another trip? (yes/no): ");
        String choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("yes")) {
            return addTripDetails(scanner); // Recursively call addTripDetails to add another trip
        } else {
            System.out.println("Exiting....");
            scanner.close();
            System.exit(0);}
        return trip;

    }
    public static String getDriverId(String driverUsername) {
        try (BufferedReader reader = new BufferedReader(new FileReader("Driver.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length >= 3 && parts[0].equals(driverUsername)) {
                    return parts[2]; // Return the driver ID if found
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading driver details from file: " + e.getMessage());}
        return null; // Return null if driver not found
    }
    public static void saveTripDetails(Trip trip) {
        String filename = "trip details.txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            // Append trip details along with driver information
            String tripDetails = trip.getTripDetails();
            if (!tripDetails.endsWith(trip.getDriverId())) { // Check if driver info is not already added
                tripDetails += ":" + trip.getName() + ":" + trip.getDriverId();}
            writer.println(tripDetails);
            System.out.println("Trip details saved successfully.");
        } catch (IOException e) {
            System.out.println("Error writing trip details to file: " + e.getMessage());}
    }

    public static void deleteTripDetails(Scanner scanner) {
        System.out.println("Enter the trip ID to delete:");
        String tripIdToDelete = scanner.nextLine();
        // Read trip details from file and check if trip ID exists
        boolean tripFound = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("trip details.txt"))) {
            StringBuilder newTripDetails = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length > 0 && parts[0].equals(tripIdToDelete)) {
                    tripFound = true;
                } else {
                    newTripDetails.append(line).append("\n");}}
            if (!tripFound) {
                System.out.println("Trip with ID " + tripIdToDelete + " not found.");
                return;}
            // Write updated trip details (without the deleted trip) back to the file
            try (PrintWriter writer = new PrintWriter(new FileWriter("trip details.txt"))) {
                writer.print(newTripDetails.toString().trim());
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());}
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());}
        System.out.println("Trip with ID " + tripIdToDelete + " deleted successfully.");}


    public static List<String> getAssignedTripDetails(String driverUsername) {
        List<String> assignedTripDetails = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("trip details.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length >= 9 && parts[8].equals(driverUsername)) {
                    // Extract trip details
                    String tripId = parts[0];
                    String source = parts[1];
                    String destination = parts[2];
                    String type = parts[3];
                    double price = Double.parseDouble(parts[4]);
                    int numberOfStops = Integer.parseInt(parts[5]);
                    String vehicleType = parts[6];
                    int availableSeats = Integer.parseInt(parts[7]);
                    // Append trip details to the list
                    assignedTripDetails.add("Trip ID: " + tripId + "\n" +
                            "Source: " + source + "\n" +
                            "Destination: " + destination + "\n" +
                            "Type: " + type + "\n" +
                            "Price: " + price + "\n" +
                            "Number of stops: " + numberOfStops + "\n" +
                            "Vehicle type: " + vehicleType + "\n" +
                            "Available Seats: " + availableSeats + "\n----------------------------------");}}
        } catch (IOException e) {
            System.out.println("Error reading trip details from file: " + e.getMessage());}
        return assignedTripDetails;}

    public static int generateRandomId() {
        Random random = new Random();
        return random.nextInt(1000);// Generate a random integer between 0 and 999
    }}