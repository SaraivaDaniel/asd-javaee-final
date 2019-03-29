package com.danielsaraiva.sharebroker.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.danielsaraiva.sharebroker.rabbitmq.Message;
import com.danielsaraiva.sharebroker.rabbitmq.RabbitMQConfig;

@Service
public class MessageServiceImpl implements MessageService{

	private final RabbitTemplate rabbitTemplate;
	 
    public MessageServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    @Override
    public void sendMessage(Message message) {
        this.rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_MESSAGES, message);
    }
}