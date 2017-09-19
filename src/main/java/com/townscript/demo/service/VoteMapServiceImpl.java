package com.townscript.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.townscript.demo.model.Song;
import com.townscript.demo.model.UserSongVoteMap;
import com.townscript.demo.repository.SongRepository;
import com.townscript.demo.repository.UserSongUpVoteMapRepository;

@Service
public class VoteMapServiceImpl implements VoteMapService{
	private static final Logger logger = Logger.getLogger(VoteMapServiceImpl.class);
	
	@Autowired
	UserSongUpVoteMapRepository voteMapRepository;

	@Autowired
	SongRepository songRepository;
	
	@Transactional
	public void upVote(long userId, long songId) {
		UserSongVoteMap songMap = new UserSongVoteMap();
		songMap.setUserId(userId);
		songMap.setSongId(songId);
		
		logger.info("Saving upvote by " + userId + " for song " + songId);
		voteMapRepository.save(songMap);
		
		Song song = songRepository.findOne(songId);
		song.setUpVote(song.getUpVote() + 1);
		songRepository.save(song);
	}

	public Map<Long, UserSongVoteMap> getUserUpvoteMap(Long userId) {
		
		Map<Long , UserSongVoteMap> userSongVoteMap = new HashMap<Long, UserSongVoteMap>();
		List<UserSongVoteMap> upvotedSongList = voteMapRepository.findAllByUserId(userId);

		if(upvotedSongList != null && !upvotedSongList.isEmpty()) {
			for(UserSongVoteMap vote : upvotedSongList) {
				userSongVoteMap.put(vote.getSongId(), vote);
			}
		}
		return userSongVoteMap;
	}
	
	@Transactional
	public void resetUpVoteCount(Long songId) {

		logger.info("Reseting votes for song " + songId);
		voteMapRepository.deleteBySongId(songId);
		
		Song song = songRepository.findOne(songId);
		song.setUpVote(0l);
		song.setCurrentSong(false);
		songRepository.save(song);
	}
}
