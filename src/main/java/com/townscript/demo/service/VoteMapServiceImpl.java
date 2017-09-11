package com.townscript.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.townscript.demo.model.Song;
import com.townscript.demo.model.UserSongVoteMap;
import com.townscript.demo.repository.SongRepository;
import com.townscript.demo.repository.UserSongUpVoteMapRepository;

@Service
public class VoteMapServiceImpl implements VoteMapService{

	@Autowired
	UserSongUpVoteMapRepository voteMapRepository;

	@Autowired
	SongRepository songRepository;
	
	@Transactional
	public void upVote(long userId, long songId) {
		UserSongVoteMap songMap = new UserSongVoteMap();
		songMap.setUserId(userId);
		songMap.setSongId(songId);
		
		System.out.println("saving vote map");
		voteMapRepository.save(songMap);
		
		Song song = songRepository.findOne(songId);
		
		System.out.println("song retrieved is " + song);
		
		song.setUpVote(song.getUpVote() + 1);
		
		songRepository.save(song);
	}

	public Map<Long, UserSongVoteMap> getUserUpvoteMap(Long userId) {
		
		Map<Long , UserSongVoteMap> userSongVoteMap = new HashMap<Long, UserSongVoteMap>();
		
		List<UserSongVoteMap> upvotedSongList = voteMapRepository.findAllByUserId(userId);
		System.out.println("list is " + upvotedSongList);
		
		if(upvotedSongList != null && !upvotedSongList.isEmpty()) {
			for(UserSongVoteMap vote : upvotedSongList) {
				userSongVoteMap.put(vote.getSongId(), vote);
			}
		}
		return userSongVoteMap;
	}
	
	@Transactional
	public void resetUpVoteCoung(Long songId) {
	
		System.out.println("deleting for song " + songId);
		voteMapRepository.deleteBySongId(songId);
		
		Song song = songRepository.findOne(songId);
		
		System.out.println("song retrieved is " + song);
		
		song.setUpVote(0l);
		
		songRepository.save(song);
	}
}
