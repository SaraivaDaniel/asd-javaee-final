package com.danielsaraiva.sharebroker.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.danielsaraiva.sharebroker.domain.Share;

public interface ShareRepository extends CrudRepository<Share, Long> {

	List<Share> findByCompanyId(Long companyId);
	
	List<Share> findByBuyerId(Long buyerId);
	
	List<Share> findByBuyerIdAndCompanyId(Long buyerId, Long companyId);
}
