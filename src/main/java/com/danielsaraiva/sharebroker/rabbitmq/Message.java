package com.danielsaraiva.sharebroker.rabbitmq;

import java.io.Serializable;

import com.danielsaraiva.sharebroker.api.v1.model.OrderDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	private String action;
	private OrderDTO body;
}