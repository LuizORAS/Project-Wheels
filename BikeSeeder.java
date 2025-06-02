package Wheels;

import java.io.IOException;
import java.util.*;

public class BikeSeeder {
    public static void seed() {
        ApiClient apiClient = new ApiClient();
        List<Bike> bikes = new ArrayList<>();
        int id = 1;
        // 5 de cada tipo
        for (BikeType type : BikeType.values()) {
            for (int i = 0; i < 5; i++) {
                bikes.add(new Bike(id++, type, true));
            }
        }
        int ok = 0, fail = 0;
        for (Bike bike : bikes) {
            try {
                boolean created = apiClient.createBike(bike);
                if (created) ok++; else fail++;
            } catch (IOException | InterruptedException e) {
                fail++;
            }
        }
    }
}