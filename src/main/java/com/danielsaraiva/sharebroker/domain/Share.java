package com.danielsaraiva.sharebroker.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Share {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Boolean isForSale;
	
	private Double originalValue;
	
	private Double currentValue;
	
	private Double saleValue;
	
	@ManyToOne
	private Company company;
	
	@ManyToOne
	private Buyer buyer;

}
