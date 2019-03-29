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

import com.danielsaraiva.sharebroker.api.v1.model.BuyerDTO;
import com.danielsaraiva.sharebroker.services.BuyerService;

@RestController
@RequestMapping(BuyerController.BASE_URL)
public class BuyerController {

	public static final String BASE_URL = "/api/v1/buyers";
	
	private final BuyerService buyerService;
	
	public BuyerController(BuyerService buyerService) {
		this.buyerService = buyerService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<BuyerDTO> getAll() {
		return buyerService.getAll();
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public BuyerDTO getById(@PathVariable Long id) {
		return buyerService.getById(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public BuyerDTO createNew(@RequestBody BuyerDTO buyerDTO) {
		return buyerService.createNew(buyerDTO);
	}
	
}
