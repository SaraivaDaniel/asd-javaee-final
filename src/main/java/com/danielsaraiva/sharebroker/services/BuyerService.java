package com.danielsaraiva.sharebroker.services;

import java.util.List;

import com.danielsaraiva.sharebroker.api.v1.model.BuyerDTO;

public interface BuyerService {

	List<BuyerDTO> getAll();
	
	BuyerDTO getById(Long id);
	
	BuyerDTO createNew(BuyerDTO BuyerDTO);
	
	BuyerDTO save(Long id, BuyerDTO BuyerDTO);
	
}
