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

    public void setId(int id) {
        this.id = id;
    }

    public BikeType getType() {
        return type;
    }

    public void setType(BikeType type) {
        this.type = type;
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