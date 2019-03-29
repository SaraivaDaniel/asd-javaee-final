package com.danielsaraiva.sharebroker.api.v1.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShareDTO {

	private Long id;

	private Boolean isForSale;
	
	private Double originalValue;
	
	private Double currentValue;
	
	private Double saleValue;
	
	private Long companyId;
	
	private Long buyerId;
}
