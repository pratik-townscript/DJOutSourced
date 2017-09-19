package com.townscript.demo.client.service;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.townscript.demo.client.model.Client;
import com.townscript.demo.client.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService{
	
	private static final Logger logger = Logger.getLogger(ClientServiceImpl.class);
	
	@Autowired
	private ClientRepository clientRepository;
	
	public void publish() {
		for(Client client : clientRepository.getAll())
		{
			try {
				//For the open connections we are going to ask clients to make new requests to get all songs 
				if(client.isOpen()){
					client.sendText("Refresh");
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
}
