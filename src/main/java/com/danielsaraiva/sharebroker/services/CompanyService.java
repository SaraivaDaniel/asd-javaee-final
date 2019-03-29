package com.danielsaraiva.sharebroker.services;

import java.util.List;

import com.danielsaraiva.sharebroker.api.v1.model.CompanyDTO;

public interface CompanyService {

	List<CompanyDTO> getAll();
	
	CompanyDTO getById(Long id);
	
	CompanyDTO createNew(CompanyDTO companyDTO);
	
	CompanyDTO save(Long id, CompanyDTO companyDTO);
	
}
