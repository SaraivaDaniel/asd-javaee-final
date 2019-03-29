package com.danielsaraiva.sharebroker.services;

import com.danielsaraiva.sharebroker.rabbitmq.Message;

public interface MessageService {

	void sendMessage(Message message);
}