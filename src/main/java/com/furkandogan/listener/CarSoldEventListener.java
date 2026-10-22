package com.furkandogan.listener;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.furkandogan.event.CarSoldEvent;
import com.furkandogan.producer.CarSoldProducer;

@Component
public class CarSoldEventListener {

    private final CarSoldProducer carSoldProducer;

    public CarSoldEventListener(CarSoldProducer carSoldProducer) {
        this.carSoldProducer = carSoldProducer;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCarSoldEvent(CarSoldEvent event) {

        carSoldProducer.sendCarSoldEvent(event);

        System.out.println(
                "Satis tamamlandi, RabbitMQ'ya mesaj gonderildi. Car ID: "
                + event.getCarId()
        );
    }
}