package com.dravid.rabbitlistener;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private static final String MY_QUEUE = "MyQueue";

    //Creating a Queue
    @Bean
    Queue myQueue() {
        return new Queue(MY_QUEUE, true);
    }

    //Creating an Exchange
    @Bean
    Exchange myExchange() {
        return ExchangeBuilder.topicExchange("MyTopicExchange").durable(true).autoDelete().build();
    }

//    public Binding(String destination, Binding.DestinationType destinationType, String exchange, String routingKey, Map<String, Object> arguments) {

    @Bean
    Binding binding() {
        // One way of creating Binding
//        return new Binding(MY_QUEUE, Binding.DestinationType.QUEUE, "MyTopicExchange", "topic", null);

        //Another way of creating Binding
        return BindingBuilder.bind(myQueue()).to(myExchange()).with("topic").noargs();
    }

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
        cachingConnectionFactory.setUsername("guest");
        cachingConnectionFactory.setPassword("guest");
        return cachingConnectionFactory;
    }

    @Bean
    MessageListenerContainer messageListenerContainer() {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory());
        simpleMessageListenerContainer.setQueues(myQueue());
        simpleMessageListenerContainer.setMessageListener(new RabbitMQMessageListener());
        return simpleMessageListenerContainer;
    }

}
