package Wheels;

public class Bike {
    private final int id;
    private final BikeType type;
    private boolean available;

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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
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