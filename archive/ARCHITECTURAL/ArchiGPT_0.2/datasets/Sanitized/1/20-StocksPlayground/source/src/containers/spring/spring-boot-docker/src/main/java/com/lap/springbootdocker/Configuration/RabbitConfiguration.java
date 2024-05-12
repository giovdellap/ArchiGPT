package com.lap.springbootdocker.Configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    public static final String RPC_MESSAGE_QUEUE = "spring_node";
    public static final String RPC_REPLY_MESSAGE_QUEUE = "node_spring_" + System.getenv("ID");
    /** *
     * Set sending RPCQueue message
     Configure the Send Message Queue*/
    @Bean
    Queue msgQueue() {

        return new Queue(RPC_MESSAGE_QUEUE, false);
    }
    /** *
     * Return Queue Configuration
     */
    @Bean
    Queue replyQueue() {
        return new Queue(RPC_REPLY_MESSAGE_QUEUE, false);
    }
    
    /** *
     * Use RabbitTemplate Send and receive messages
     * And set callback queue address
     */
    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {

        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setReplyAddress(RPC_REPLY_MESSAGE_QUEUE);
        return template;
    }
    /** *
     * Configure listener for return queue
     */
    @Bean
    SimpleMessageListenerContainer replyContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(RPC_REPLY_MESSAGE_QUEUE);
        container.setMessageListener(rabbitTemplate(connectionFactory));
        return container;
    }
}

