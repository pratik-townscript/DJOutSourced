package com.townscript.demo.websocket;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import com.townscript.demo.client.model.Client;
import com.townscript.demo.client.repository.ClientRepository;

@ServerEndpoint(value = "/getRealSongList", configurator = SpringConfigurator.class)
public class SongWebSocketHandler {
	@Autowired
	private ClientRepository clientRepository;
	
	@OnOpen
	public void onOpen(Session session) {
		System.out.println("session id is " + session.getId());
		System.out.println("Received new session " + session);
		clientRepository.add(new Client(session));
	}
	
	@OnClose
	public void onClose(CloseReason reason, Session session) {
		System.out.println("removing the session " + session.getId());
		System.out.println("Closing with reason " + reason + " for the clients " + session);
		clientRepository.remove(new Client(session));
	}
}
