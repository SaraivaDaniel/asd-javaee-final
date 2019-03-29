package com.danielsaraiva.sharebroker.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Buyer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "buyer")
	private Set<Share> shares = new HashSet<>();
}
