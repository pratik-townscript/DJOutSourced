package com.townscript.demo.client.model;

import java.io.IOException;

import javax.websocket.Session;

public class Client {

	//private final String userId;
	private final Session session;
	
	public Client(//String userId, 
				  Session session) {
		super();
		//this.userId = userId;
		this.session = session;
	}

	/*public String getUserId() {
		return userId;
	}*/

	public Session getSession() {
		return session;
	}
	
	public void sendText(String text) throws IOException {
		this.session.getBasicRemote().sendText(text);
	}
	
	public boolean isOpen()
	{
		return session.isOpen();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((session == null) ? 0 : session.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (session == null) {
			if (other.session != null)
				return false;
		} else if (session.getId().equals(other.session.getId()))
			return false;
		return true;
	}
	
	/*public void sendObject(Object object) throws Exception {
		this.session.getBasicRemote().sendObject(object);
	}*/
	
	
}
