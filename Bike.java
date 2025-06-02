package Wheels;

public class Bike {
    private int id;
    private BikeType type;
    private boolean available;
    public Bike() {}
    public Bike(int id, BikeType type, boolean available) {
        this.id = id;
        this.type = type;
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public BikeType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Bike{" +
                "id=" + id +
                ", type=" + type +
                ", available=" + available +
                '}';
    }
}