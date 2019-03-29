package com.danielsaraiva.sharebroker.api.v1.mapper;

import org.springframework.stereotype.Component;

import com.danielsaraiva.sharebroker.api.v1.model.BuyerDTO;
import com.danielsaraiva.sharebroker.domain.Buyer;

@Component
public class BuyerMapper {

	public BuyerDTO buyerToBuyerDTO(Buyer buyer) {
		final BuyerDTO buyerDTO = new BuyerDTO();
		
		buyerDTO.setId(buyer.getId());
		buyerDTO.setName(buyer.getName());
		buyerDTO.setEmail(buyer.getEmail());
		
		return buyerDTO;
	}

	public Buyer buyerDTOToBuyer(BuyerDTO buyerDTO) {
		final Buyer buyer = new Buyer();
		
		buyer.setId(buyerDTO.getId());
		buyer.setName(buyerDTO.getName());
		buyer.setEmail(buyerDTO.getEmail());
		
		return buyer;
	}
}
