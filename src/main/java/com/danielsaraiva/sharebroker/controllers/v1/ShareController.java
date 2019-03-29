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

import com.danielsaraiva.sharebroker.api.v1.model.NewShareDTO;
import com.danielsaraiva.sharebroker.api.v1.model.ShareDTO;
import com.danielsaraiva.sharebroker.services.ShareService;

@RestController
@RequestMapping(ShareController.BASE_URL)
public class ShareController {

	public static final String BASE_URL = "/api/v1/shares";
	
	private final ShareService shareService;
	
	public ShareController(ShareService shareService) {
		this.shareService = shareService;
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<ShareDTO> getAll() {
		return shareService.getAll();
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ShareDTO getById(@PathVariable Long id) {
		return shareService.getById(id);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public List<ShareDTO> createNew(@RequestBody NewShareDTO newShareDTO) {
		return shareService.createNew(newShareDTO);
	}
	
	@GetMapping("/company/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<ShareDTO> getByCompanyId(@PathVariable Long id) {
		return shareService.getByCompanyId(id);
	}
	
	@GetMapping("/buyer/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<ShareDTO> getByBuyerId(@PathVariable Long id) {
		return shareService.getByBuyerId(id);
	}
	
}
