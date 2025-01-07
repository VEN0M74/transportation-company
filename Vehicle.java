public class Vehicle {
    private String type;

    public static final Vehicle LIMOUSINE = new Vehicle("Limousine");
    public static final Vehicle BUS = new Vehicle("Bus");
    public static final Vehicle MINIBUS = new Vehicle("Minibus");
    private Vehicle(String type) {
        this.type = type;

    }

    public String getType() {
        return type;
    }


}


