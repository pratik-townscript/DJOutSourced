package com.townscript.demo.client.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.townscript.demo.client.model.Client;
import com.townscript.demo.client.repository.ClientRepository;
import com.townscript.demo.model.Song;
import com.townscript.demo.service.SongService;

@Service
public class ClientServiceImpl implements ClientService{

	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private SongService songService;

	private Gson gson = new Gson();
	
	public void publish() {
		
		List<Song> songsList = songService.findAllSongs();
		String returnVal = this.gson.toJson(songsList);
		System.out.println("song list is " + songsList);
		System.out.println("sending data to client " + returnVal);
		for(Client client : clientRepository.getAll())
		{
			try {
				if(client.isOpen()){
					client.sendText(returnVal);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
