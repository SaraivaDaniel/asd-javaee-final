package com.danielsaraiva.sharebroker.api.v1.model;

import org.springframework.data.annotation.ReadOnlyProperty;

import com.danielsaraiva.sharebroker.domain.Buyer;
import com.danielsaraiva.sharebroker.domain.Company;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShareDTO {

	private Long id;

	@ReadOnlyProperty
	private Boolean isForSale;
	
	@ReadOnlyProperty
	private Double originalValue;
	
	@ReadOnlyProperty
	private Double currentValue;
	
	@ReadOnlyProperty
	private Double saleValue;
	
	@ReadOnlyProperty
	private Company company;
	
	@ReadOnlyProperty
	private Buyer buyer;
}
