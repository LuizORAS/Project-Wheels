package Wheels;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.List;

public class ApiClient {
    private static final String BASE_URL = "https://project-wheels-api.onrender.com";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ApiClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    // ---- USERS ----

    public User getUserByEmail(String email) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/users/" + email))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 404) return null;
        return objectMapper.readValue(response.body(), User.class);
    }

    public boolean createUser(User user) throws IOException, InterruptedException {
        String json = objectMapper.writeValueAsString(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/users"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
        return response.statusCode() == 200 || response.statusCode() == 201;
    }

    public boolean deleteUser(String email) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/users/" + email))
                .DELETE()
                .build();
        HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
        return response.statusCode() == 200 || response.statusCode() == 204;
    }

    public boolean changeUserPlan(String email, Plan newPlan) throws IOException, InterruptedException {
        String json = objectMapper.writeValueAsString(newPlan);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/users/" + email + "/plan"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
        return response.statusCode() == 200 || response.statusCode() == 204;
    }

    public boolean cancelUserPlan(String email) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/users/" + email + "/plan/cancel"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
        return response.statusCode() == 200 || response.statusCode() == 204;
    }

    // ---- BIKES ----

    public boolean createBike(Bike bike) throws IOException, InterruptedException {
        String json = objectMapper.writeValueAsString(bike);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/bikes"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
        return response.statusCode() == 200 || response.statusCode() == 201;
    }

    public List<Bike> getAvailableBikes(BikeType type) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/bikes/available?type=" + type.name()))
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), new TypeReference<List<Bike>>() {});
    }
    public boolean rentBike(int bikeId, String userEmail) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/bikes/" + bikeId + "/rent?userEmail=" + userEmail))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
        return response.statusCode() == 200 || response.statusCode() == 204;
    }
    public boolean returnBike(int bikeId, String userEmail) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/bikes/" + bikeId + "/return?userEmail=" + userEmail))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
        return response.statusCode() == 200 || response.statusCode() == 204;
    }
}