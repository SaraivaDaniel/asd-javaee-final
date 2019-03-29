package com.danielsaraiva.sharebroker.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.danielsaraiva.sharebroker.api.v1.mapper.CompanyMapper;
import com.danielsaraiva.sharebroker.api.v1.model.CompanyDTO;
import com.danielsaraiva.sharebroker.domain.Company;
import com.danielsaraiva.sharebroker.repositories.CompanyRepository;

@Service
public class CompanyServiceImpl implements CompanyService {
	
	private CompanyRepository companyRepository;
	
	private final CompanyMapper companyMapper;
	
	public CompanyServiceImpl(CompanyRepository companyRepository, CompanyMapper companyMapper) {
		this.companyRepository = companyRepository;
		this.companyMapper = companyMapper;
	}

	@Override
	public List<CompanyDTO> getAll() {
		return StreamSupport.stream(this.companyRepository.findAll().spliterator(), false)
				.map(companyMapper::companyToCompanyDTO)
				.collect(Collectors.toList());
	}

	@Override
	public CompanyDTO getById(Long id) {
		Company company = getCompanyById(id);
		return companyMapper.companyToCompanyDTO(company);
	}
	
	private Company getCompanyById(Long id) {
		Optional<Company> companyOptional = companyRepository.findById(id);
		
		if (!companyOptional.isPresent()) {
			throw new IllegalArgumentException("Empresa não localizada para ID: " + id.toString());
		}
		
		return companyOptional.get();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public CompanyDTO createNew(CompanyDTO companyDTO) {
		Company detachedCompany = companyMapper.companyDTOToCompany(companyDTO);
		Company companySaved = companyRepository.save(detachedCompany);
		return companyMapper.companyToCompanyDTO(companySaved);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public CompanyDTO save(Long id, CompanyDTO companyDTO) {
		Company detachedCompany = companyMapper.companyDTOToCompany(companyDTO);
		detachedCompany.setId(id);
		
		Company companySaved = companyRepository.save(detachedCompany);
		return companyMapper.companyToCompanyDTO(companySaved);
	}

}
