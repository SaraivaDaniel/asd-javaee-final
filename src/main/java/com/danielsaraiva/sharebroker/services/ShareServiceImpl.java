package com.danielsaraiva.sharebroker.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.danielsaraiva.sharebroker.api.v1.mapper.ShareMapper;
import com.danielsaraiva.sharebroker.api.v1.model.NewShareDTO;
import com.danielsaraiva.sharebroker.api.v1.model.ShareDTO;
import com.danielsaraiva.sharebroker.domain.Company;
import com.danielsaraiva.sharebroker.domain.Share;
import com.danielsaraiva.sharebroker.repositories.CompanyRepository;
import com.danielsaraiva.sharebroker.repositories.ShareRepository;

@Service
public class ShareServiceImpl implements ShareService {
	
	private ShareRepository shareRepository;
	private CompanyRepository companyRepository;
	
	private final ShareMapper shareMapper;
	
	public ShareServiceImpl(ShareRepository shareRepository, 
			CompanyRepository companyRepository,
			ShareMapper shareMapper) {
		this.shareRepository = shareRepository;
		this.companyRepository = companyRepository;
		this.shareMapper = shareMapper;
	}

	@Override
	public List<ShareDTO> getAll() {
		return StreamSupport.stream(this.shareRepository.findAll().spliterator(), false)
				.map(shareMapper::shareToShareDTO)
				.collect(Collectors.toList());
	}

	@Override
	public ShareDTO getById(Long id) {
		Share share = getShareById(id);
		return shareMapper.shareToShareDTO(share);
	}
	
	private Share getShareById(Long id) {
		Optional<Share> shareOptional = shareRepository.findById(id);
		
		if (!shareOptional.isPresent()) {
			throw new IllegalArgumentException("Empresa não localizada para ID: " + id.toString());
		}
		
		return shareOptional.get();
	}

	@Override
	public List<ShareDTO> createNew(NewShareDTO newShareDTO) {
		Company company = getCompanyById(newShareDTO.getCompanyId());
		
		List<ShareDTO> result = new ArrayList<ShareDTO>();
		
		for (int i = 0; i < newShareDTO.getQuantity(); i++) {
			Share detachedShare = new Share();
			detachedShare.setCompany(company);
			detachedShare.setCurrentValue(newShareDTO.getValue());
			detachedShare.setSaleValue(newShareDTO.getValue());
			detachedShare.setOriginalValue(newShareDTO.getValue());
			detachedShare.setIsForSale(true);
			
			Share shareSaved = shareRepository.save(detachedShare);
			ShareDTO shareDTOSaved = shareMapper.shareToShareDTO(shareSaved);
			result.add(shareDTOSaved);
		}
		
		return result;
	}
	
	private Company getCompanyById(Long id) {
		Optional<Company> companyOptional = companyRepository.findById(id);
		
		if (!companyOptional.isPresent()) {
			throw new IllegalArgumentException("Empresa não localizada para ID: " + id.toString());
		}
		
		return companyOptional.get();
	}

	@Override
	public List<ShareDTO> getByCompanyId(Long id) {
		return StreamSupport.stream(this.shareRepository.findByCompanyId(id).spliterator(), false)
				.map(shareMapper::shareToShareDTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<ShareDTO> getByBuyerId(Long id) {
		return StreamSupport.stream(this.shareRepository.findByBuyerId(id).spliterator(), false)
				.map(shareMapper::shareToShareDTO)
				.collect(Collectors.toList());
	}

	
}
