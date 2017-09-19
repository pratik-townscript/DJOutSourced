package com.townscript.demo.websocket;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import com.townscript.demo.client.model.Client;
import com.townscript.demo.client.repository.ClientRepository;

@ServerEndpoint(value = "/getRealSongList", configurator = SpringConfigurator.class)
public class SongWebSocketHandler {
	private static final Logger logger = Logger.getLogger(SongWebSocketHandler.class);
	
	@Autowired
	private ClientRepository clientRepository;
	
	@OnOpen
	public void onOpen(Session session) {
		logger.info("Opening new session with id " + session.getId());
		clientRepository.add(new Client(session));
	}
	
	@OnClose
	public void onClose(CloseReason reason, Session session) {
		logger.info("Closing with reason " + reason + " for the clients " + session.getId());
		clientRepository.remove(new Client(session));
	}
	
	@OnError
	public void onError(Session session , Throwable error) {
		logger.error("There was some error while connecting to client " + session.getId());
		logger.error(error.getMessage(), error);
	}
}
