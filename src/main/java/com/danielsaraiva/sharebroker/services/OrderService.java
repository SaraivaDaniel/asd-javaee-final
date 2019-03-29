package com.danielsaraiva.sharebroker.services;

import com.danielsaraiva.sharebroker.api.v1.model.BuyOrderDTO;
import com.danielsaraiva.sharebroker.api.v1.model.SellOrderDTO;

public interface OrderService {

	SellOrderDTO newSellOrder(SellOrderDTO sellOrderDTO);
	
	BuyOrderDTO newBuyOrder(BuyOrderDTO buyOrderDTO);
	
	SellOrderDTO executeSellOrderDTO(SellOrderDTO sellOrderDTO);
	
	BuyOrderDTO executeBuyOrderDTO(BuyOrderDTO buyOrderDTO);
	
}
