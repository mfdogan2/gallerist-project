package com.furkandogan.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.furkandogan.config.RabbitMQConfig;
import com.furkandogan.event.CarSoldEvent;

@Component
public class CarSoldProducer {

    private final RabbitTemplate rabbitTemplate;

    public CarSoldProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendCarSoldEvent(CarSoldEvent event) {

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                event
        );
    }
}