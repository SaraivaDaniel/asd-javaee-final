package com.danielsaraiva.sharebroker.services;

import com.danielsaraiva.sharebroker.api.v1.model.OrderDTO;

public interface OrderService {

	OrderDTO newSellOrder(OrderDTO sellOrderDTO);
	
	OrderDTO newBuyOrder(OrderDTO buyOrderDTO);
	
	void executeSellOrder(OrderDTO sellOrderDTO);
	
	void executeBuyOrder(OrderDTO buyOrderDTO);
	
}
