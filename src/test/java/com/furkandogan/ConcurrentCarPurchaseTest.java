package com.furkandogan;




import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.Test;

public class ConcurrentCarPurchaseTest {

    @Test
    void concurrentPurchaseTest() throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch startLatch = new CountDownLatch(1);

        try {
            HttpClient client = HttpClient.newHttpClient();

            Callable<Integer> customer1 = () ->
                    buyCar(client, startLatch, 1L);

            Callable<Integer> customer2 = () ->
                    buyCar(client, startLatch, 2L);

            Future<Integer> result1 = executor.submit(customer1);
            Future<Integer> result2 = executor.submit(customer2);

            startLatch.countDown();

            System.out.println("Müşteri 1 HTTP Status: " + result1.get());
            System.out.println("Müşteri 2 HTTP Status: " + result2.get());

        } finally {
            executor.shutdown();
        }
    }

    private int buyCar(
            HttpClient client,
            CountDownLatch startLatch,
            Long customerId) throws Exception {

        startLatch.await();
        String token = System.getenv("GALLERIST_TEST_TOKEN");
        String json = """
                {
                    "customerId": %d,
                    "galleristId": 5,
                    "carId": 5
                }
                """.formatted(customerId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/rest/api/saled-car/save"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        System.out.println("Customer " + customerId
                + " Response: " + response.body());

        return response.statusCode();
    }
}

