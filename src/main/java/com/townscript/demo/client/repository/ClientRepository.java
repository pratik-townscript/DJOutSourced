package com.townscript.demo.client.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.townscript.demo.client.model.Client;

@Repository
@Scope("singleton")
public class ClientRepository {
	private List<Client> clients = new ArrayList<Client>();
	
	public void add(Client client) {
		synchronized (this.clients) {
			this.clients.add(client);
		}
	}
	
	public void remove(Client client) {
		synchronized (this.clients) {
			this.clients.remove(client);
		}
	}
	
	public List<Client> getAll() {
		return new ArrayList<Client>(this.clients);
	}
}
