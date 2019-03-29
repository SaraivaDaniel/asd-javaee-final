package com.danielsaraiva.sharebroker.repositories;

import org.springframework.data.repository.CrudRepository;

import com.danielsaraiva.sharebroker.domain.Buyer;

public interface BuyerRepository extends CrudRepository<Buyer, Long> {

}
