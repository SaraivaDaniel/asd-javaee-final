package com.danielsaraiva.sharebroker.api.v1.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDTO {

	private Long id;
	
	@NotNull
	@Size(min = 2, max = 255)
	private String name;
	
}
