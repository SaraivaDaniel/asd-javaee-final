package com.danielsaraiva.sharebroker.api.v1.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuyOrderDTO {

	private Long buyerId;
	private Long companyId;
	private Double value;
	private Integer quantity;

}
