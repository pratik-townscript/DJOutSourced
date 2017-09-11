package com.townscript.demo.service;

import java.util.Map;

import com.townscript.demo.model.UserSongVoteMap;

public interface VoteMapService {
	void upVote(long userId , long songId);
	
	Map<Long , UserSongVoteMap> getUserUpvoteMap(Long userId);
	
	void resetUpVoteCoung(Long songId);
}

