package com.furkandogan.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.furkandogan.config.RabbitMQConfig;
import com.furkandogan.event.CarSoldEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import java.time.Duration;

@Component
public class CarSoldConsumer {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void consumeCarSoldEvent(CarSoldEvent event) {

        String redisKey = "processed:event:" + event.getEventId();

        Boolean isFirst = stringRedisTemplate.opsForValue()
                .setIfAbsent(redisKey, "PROCESSING", Duration.ofHours(24));

        if (!Boolean.TRUE.equals(isFirst)) {
            System.out.println("Mesaj zaten işleniyor veya işlendi: " + event.getEventId());
            return;
        }

        try {
            System.out.println("RabbitMQ'dan mesaj alindi!");
            System.out.println("Car ID: " + event.getCarId());
            System.out.println("Customer ID: " + event.getCustomerId());
            System.out.println("Event Type: " + event.getEventType());
            System.out.println("Event ID: " + event.getEventId());

            // Mesaj başarıyla işlendi
            stringRedisTemplate.opsForValue()
                    .set(redisKey, "PROCESSED", Duration.ofHours(24));

        } catch (Exception e) {

            // Hata oluşursa PROCESSING kaydını temizle
            stringRedisTemplate.delete(redisKey);

            // RabbitMQ Retry mekanizmasının çalışması için hatayı tekrar fırlat
            throw e;
        }
    }
}