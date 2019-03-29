package com.danielsaraiva.sharebroker.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.danielsaraiva.sharebroker.api.v1.mapper.BuyerMapper;
import com.danielsaraiva.sharebroker.api.v1.model.BuyerDTO;
import com.danielsaraiva.sharebroker.domain.Buyer;
import com.danielsaraiva.sharebroker.repositories.BuyerRepository;

@Service
public class BuyerServiceImpl implements BuyerService {
	
	private BuyerRepository buyerRepository;
	
	private final BuyerMapper buyerMapper;
	
	public BuyerServiceImpl(BuyerRepository buyerRepository, BuyerMapper buyerMapper) {
		this.buyerRepository = buyerRepository;
		this.buyerMapper = buyerMapper;
	}

	@Override
	public List<BuyerDTO> getAll() {
		return StreamSupport.stream(this.buyerRepository.findAll().spliterator(), false)
				.map(buyerMapper::buyerToBuyerDTO)
				.collect(Collectors.toList());
	}

	@Override
	public BuyerDTO getById(Long id) {
		Buyer buyer = getBuyerById(id);
		return buyerMapper.buyerToBuyerDTO(buyer);
	}
	
	private Buyer getBuyerById(Long id) {
		Optional<Buyer> buyerOptional = buyerRepository.findById(id);
		
		if (!buyerOptional.isPresent()) {
			throw new IllegalArgumentException("Empresa não localizada para ID: " + id.toString());
		}
		
		return buyerOptional.get();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public BuyerDTO createNew(BuyerDTO buyerDTO) {
		Buyer detachedBuyer = buyerMapper.buyerDTOToBuyer(buyerDTO);
		Buyer buyerSaved = buyerRepository.save(detachedBuyer);
		return buyerMapper.buyerToBuyerDTO(buyerSaved);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public BuyerDTO save(Long id, BuyerDTO buyerDTO) {
		Buyer detachedBuyer = buyerMapper.buyerDTOToBuyer(buyerDTO);
		detachedBuyer.setId(id);
		
		Buyer buyerSaved = buyerRepository.save(detachedBuyer);
		return buyerMapper.buyerToBuyerDTO(buyerSaved);
	}

}
