package com.danielsaraiva.sharebroker.controllers.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.danielsaraiva.sharebroker.api.v1.model.CompanyDTO;
import com.danielsaraiva.sharebroker.services.CompanyService;

@RestController
@RequestMapping(CompanyController.BASE_URL)
public class CompanyController {

	public static final String BASE_URL = "/api/v1/companies";
	
	private final CompanyService companyService;
	
	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<CompanyDTO> getAll() {
		return companyService.getAll();
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public CompanyDTO getById(@PathVariable Long id) {
		return companyService.getById(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CompanyDTO createNew(@RequestBody CompanyDTO companyDTO) {
		return companyService.createNew(companyDTO);
	}
	
}
