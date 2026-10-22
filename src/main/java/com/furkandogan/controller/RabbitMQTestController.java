package com.furkandogan.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.furkandogan.event.CarSoldEvent;
import com.furkandogan.producer.CarSoldProducer;

@RestController
@RequestMapping("/rest/api/rabbitmq")
public class RabbitMQTestController {

    private final CarSoldProducer carSoldProducer;

    public RabbitMQTestController(CarSoldProducer carSoldProducer) {
        this.carSoldProducer = carSoldProducer;
    }

    @PostMapping("/test")
    public ResponseEntity<String> testRabbitMQ() {

        CarSoldEvent event = new CarSoldEvent(
                5L,
                2L,
                "CAR_SOLD"
        );

        carSoldProducer.sendCarSoldEvent(event);

        return ResponseEntity.ok("RabbitMQ mesaji gonderildi");
    }
}