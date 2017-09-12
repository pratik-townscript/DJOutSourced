package com.townscript.demo.web;

import java.util.HashMap;
import java.util.Map;

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
	
	@Autowired
	private VoteMapService voteMapService; 
	
	@Autowired
	private ClientService clientService;
	
	@RequestMapping(value="/upvoteSong" , method=RequestMethod.POST)
	public @ResponseBody APIResponse upVoteSong(@RequestParam(name="songId") long songId,
						   @RequestParam(name="userId") long userId)
	{
		System.out.println("upvote method invokedv with song id " + songId + " and user " + userId);
		
		voteMapService.upVote(userId, songId);
		clientService.publish();
		return APIResponse.toOkResponse("Successful in upvoting");
	}
	
	@RequestMapping(value="/getUserUpvotedSongs" , method=RequestMethod.GET)
	public @ResponseBody APIResponse userUpVotedSongs(@RequestParam(name="userId") long userId)
	{
		System.out.println("user id is " + userId);
		Map<Long , UserSongVoteMap> voteMap = voteMapService.getUserUpvoteMap(userId);
		return APIResponse.toOkResponse(voteMap);
	}
	
	@RequestMapping(value="/resetSongUpVotes" , method=RequestMethod.POST)
	public @ResponseBody APIResponse resetSongUpVoteCount(@RequestParam(name="songId") long songId)
	{
		System.out.println("song id is " + songId);
		
		voteMapService.resetUpVoteCount(songId);
		clientService.publish();
		return APIResponse.toOkResponse("Successfully reset the song count");
	}
	
}
