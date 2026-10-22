package com.furkandogan.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarSoldEvent {

    private Long carId;

    private Long customerId;

    private String eventType;
}