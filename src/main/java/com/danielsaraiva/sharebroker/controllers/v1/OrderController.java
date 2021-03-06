package com.danielsaraiva.sharebroker.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.danielsaraiva.sharebroker.api.v1.model.OrderDTO;
import com.danielsaraiva.sharebroker.services.OrderService;

@RestController
@RequestMapping(OrderController.BASE_URL)
public class OrderController {
	
	public static final String BASE_URL = "/api/v1/orders";
	
	private final OrderService orderService;
	
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@PostMapping("/sell")
	@ResponseStatus(HttpStatus.CREATED)
	public OrderDTO sellOrder(@RequestBody OrderDTO sellOrderDTO) {
		return orderService.newSellOrder(sellOrderDTO);
	}
	
	@PostMapping("/buy")
	@ResponseStatus(HttpStatus.CREATED)
	public OrderDTO buyOrder(@RequestBody OrderDTO buyOrderDTO) {
		return orderService.newBuyOrder(buyOrderDTO);
	}

}
