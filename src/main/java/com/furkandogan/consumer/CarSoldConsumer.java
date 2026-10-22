package com.furkandogan.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.furkandogan.config.RabbitMQConfig;
import com.furkandogan.event.CarSoldEvent;

@Component
public class CarSoldConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void consumeCarSoldEvent(CarSoldEvent event) {

        System.out.println("RabbitMQ'dan mesaj alindi!");

        System.out.println("Car ID: " + event.getCarId());

        System.out.println("Customer ID: " + event.getCustomerId());

        System.out.println("Event Type: " + event.getEventType());
    }
}