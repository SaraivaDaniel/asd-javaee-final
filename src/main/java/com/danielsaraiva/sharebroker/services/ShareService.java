package com.danielsaraiva.sharebroker.services;

import java.util.List;

import com.danielsaraiva.sharebroker.api.v1.model.NewShareDTO;
import com.danielsaraiva.sharebroker.api.v1.model.ShareDTO;

public interface ShareService {

	List<ShareDTO> getAll();
	
	ShareDTO getById(Long id);
	
	List<ShareDTO> createNew(NewShareDTO newShareDTO);
	
	List<ShareDTO> getByCompanyId(Long id);
	
	List<ShareDTO> getByBuyerId(Long id);
	
}
