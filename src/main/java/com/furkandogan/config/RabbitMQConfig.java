package com.furkandogan.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "gallerist.sales.queue";

    public static final String EXCHANGE_NAME = "gallerist.sales.exchange";

    public static final String ROUTING_KEY = "car.sold";

    @Bean
    public Queue salesQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public DirectExchange salesExchange() {
        return new DirectExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    public Binding salesBinding(Queue salesQueue,
                                DirectExchange salesExchange) {

        return BindingBuilder
                .bind(salesQueue)
                .to(salesExchange)
                .with(ROUTING_KEY);
    }
    
    @Bean
    public JacksonJsonMessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}