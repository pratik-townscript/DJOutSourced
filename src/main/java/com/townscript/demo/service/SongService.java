package com.townscript.demo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.townscript.demo.model.Song;

public interface SongService {

	void save(String songTitle, String userName, MultipartFile songFile);
	
	List<Song> findAllSongs();

	void setSongAsPlaying(long songId);
}
