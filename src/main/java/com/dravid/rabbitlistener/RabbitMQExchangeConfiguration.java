package com.dravid.rabbitlistener;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQExchangeConfiguration {

    //Different ways of creating Exchanges
    @Bean
    Exchange exampleExchange() {
        return new TopicExchange("ExampleExchange");
    }

    @Bean
    Exchange example2ndExchange() {
        return ExchangeBuilder.directExchange("Example2ndExchange").autoDelete().internal().build();
    }

    @Bean
    Exchange newExchange() {
        return ExchangeBuilder.topicExchange("TopicTestExchange").autoDelete().durable(true).internal().build();
    }

    @Bean
    Exchange fanOutExchange() {
        return ExchangeBuilder.fanoutExchange("FanOutTestExchange").autoDelete().durable(false).internal().build();
    }

    @Bean
    Exchange headersExchange() {
        return ExchangeBuilder.headersExchange("HeadersTestExchange").internal().durable(true).ignoreDeclarationExceptions().build();
    }


}
