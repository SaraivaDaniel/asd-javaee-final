package com.danielsaraiva.sharebroker.repositories;

import org.springframework.data.repository.CrudRepository;

import com.danielsaraiva.sharebroker.domain.Share;

public interface ShareRepository extends CrudRepository<Share, Long> {

}
