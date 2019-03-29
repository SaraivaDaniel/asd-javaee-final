package com.danielsaraiva.sharebroker.repositories;

import org.springframework.data.repository.CrudRepository;

import com.danielsaraiva.sharebroker.domain.Company;

public interface CompanyRepository extends CrudRepository<Company, Long> {

}
