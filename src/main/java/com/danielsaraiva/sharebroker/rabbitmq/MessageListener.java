package com.danielsaraiva.sharebroker.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.danielsaraiva.sharebroker.rabbitmq.RabbitMQConfig;
import com.danielsaraiva.sharebroker.services.OrderService;
import com.danielsaraiva.sharebroker.services.OrderServiceImpl;
import com.danielsaraiva.sharebroker.api.v1.model.OrderDTO;
import com.danielsaraiva.sharebroker.rabbitmq.Message;

@Component
public class MessageListener {

	private final OrderService orderService;

	public MessageListener(OrderService orderService) {
		this.orderService = orderService;
	}

	@RabbitListener(queues = RabbitMQConfig.QUEUE_MESSAGES)
    public void processMessage(Message message) {
    	OrderDTO orderDTO = message.getBody();
    	
    	System.out.println("Recebeu mensagem " + message.getAction());
    	System.out.format("%s %s %s %s\n", orderDTO.getBuyerId(), orderDTO.getCompanyId(), orderDTO.getQuantity(), orderDTO.getValue());
    	
    	try {
    		if (message.getAction().equalsIgnoreCase(OrderServiceImpl.ACTION_BUY)) {
        		System.out.println("Executa compra");
        		orderService.executeBuyOrder(message.getBody());
        	} else if (message.getAction().equalsIgnoreCase(OrderServiceImpl.ACTION_SELL)) {
        		System.out.println("Executa venda");
        		orderService.executeSellOrder(message.getBody());
        	} else {
        		System.out.println("Acao não prevista: " + message.getAction());
        	}
		} catch (Exception e) {
			// em caso de exceção, loga o erro no console apenas
			e.printStackTrace();
		}
    }
}