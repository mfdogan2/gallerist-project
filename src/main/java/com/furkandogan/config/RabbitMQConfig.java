package com.furkandogan.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.QueueBuilder;
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
    public static final String DLQ_NAME = "gallerist.sales.dlq";
    public static final String DLX_NAME = "gallerist.sales.dlx";

    @Bean
    
    public Queue salesQueue() {

        return QueueBuilder.durable(QUEUE_NAME)
                .deadLetterExchange(DLX_NAME)
                .deadLetterRoutingKey("car.sold.failed")
                .build();
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
    @Bean
    public Queue deadLetterQueue() {
        return new Queue(DLQ_NAME, true);
    }
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DLX_NAME, true, false);
    }
    @Bean
    public Binding deadLetterBinding(
            Queue deadLetterQueue,
            DirectExchange deadLetterExchange) {

        return BindingBuilder
                .bind(deadLetterQueue)
                .to(deadLetterExchange)
                .with("car.sold.failed");
    }
}