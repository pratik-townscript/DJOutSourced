package com.townscript.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.townscript.demo.model.Song;
import com.townscript.demo.model.User;
import com.townscript.demo.repository.SongRepository;
import com.townscript.demo.repository.UserRepository;

@Service
public class SongServiceImpl implements SongService{
	private static final Logger logger = Logger.getLogger(SongServiceImpl.class);
	
	@Autowired private UserRepository userRepository;
	@Autowired private SongRepository songRepository;
	@Autowired private AmazonS3Service amazonService;
	
	@Transactional
	public void save(String songTitle, String userName, MultipartFile songFile) {
		
		User user = userRepository.findByUsername(userName);
		Song song = new Song();
		song.setTitle(songTitle);
		song.setUser(user);
		
		logger.info("User " + userName + " uploaded file " + songTitle + " .Saving in database");
		Song savedSong = songRepository.save(song);
		
		String fileNameInAws = savedSong.getId() + ".mp3";
		try {
			amazonService.addSongToBucket(fileNameInAws, songFile);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Song> findAllSongs() {
		List<Song> songList = songRepository.findAll();
		//List<Song> songList = songRepository.findAllOrderById();
		return songList;
	}
	
	public void setSongAsPlaying(long songId) {
		
		logger.info("Setting song with id as currently playing " + songId);
		
		Song song = songRepository.findOne(songId);
		
		song.setCurrentSong(true);
		songRepository.save(song);
	}	
}
