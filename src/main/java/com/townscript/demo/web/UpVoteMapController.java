package com.townscript.demo.web;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.townscript.demo.api.APIResponse;
import com.townscript.demo.client.service.ClientService;
import com.townscript.demo.model.UserSongVoteMap;
import com.townscript.demo.service.VoteMapService;

@Controller
public class UpVoteMapController {
	private static final Logger logger = Logger.getLogger(UpVoteMapController.class);
	
	@Autowired
	private VoteMapService voteMapService; 
	@Autowired
	private ClientService clientService;
	
	@RequestMapping(value="/upvoteSong" , method=RequestMethod.POST)
	public @ResponseBody APIResponse upVoteSong(@RequestParam(name="songId") long songId,
						   @RequestParam(name="userId") long userId)
	{
		logger.info("Upvote method invoked with song id " + songId + " and user " + userId);
		voteMapService.upVote(userId, songId);
		clientService.publish();
		return APIResponse.toOkResponse("Successful in upvoting");
	}
	
	@RequestMapping(value="/getUserUpvotedSongs" , method=RequestMethod.GET)
	public @ResponseBody APIResponse userUpVotedSongs(@RequestParam(name="userId") long userId)
	{
		logger.info("Retrieving upvoted songs for user id is " + userId);
		Map<Long , UserSongVoteMap> voteMap = voteMapService.getUserUpvoteMap(userId);
		return APIResponse.toOkResponse(voteMap);
	}
	
	@RequestMapping(value="/resetSongUpVotes" , method=RequestMethod.POST)
	public @ResponseBody APIResponse resetSongUpVoteCount(@RequestParam(name="songId") long songId)
	{
		logger.info("Reset song vote count for song id " + songId);
		
		voteMapService.resetUpVoteCount(songId);
		clientService.publish();
		return APIResponse.toOkResponse("Successfully reset the song count");
	}
	
}
