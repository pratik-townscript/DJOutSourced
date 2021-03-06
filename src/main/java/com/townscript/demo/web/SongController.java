package com.townscript.demo.web;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.townscript.demo.api.APIResponse;
import com.townscript.demo.client.service.ClientService;
import com.townscript.demo.model.Song;
import com.townscript.demo.service.SongService;

@Controller
public class SongController {
	private static final Logger logger = Logger.getLogger(SongController.class);
	protected static final String JSON_API_CONTENT_HEADER = "Content-type=application/json";
	
	@Autowired
	private SongService songService;
	@Autowired
	private ClientService clientService;
	
	@RequestMapping(value="/uploadUserSong", method=RequestMethod.POST)
	public @ResponseBody APIResponse uploadSong( @RequestParam("songTitle") String songTitle,
												 @RequestParam("username") String userName,
												 @RequestParam("file") MultipartFile songFile)
	{
		logger.info("Uploading Song by user: " + userName + " , song title is " + songTitle);
		
		if(songFile.isEmpty())
		{
			return APIResponse.toErrorResponse("Unable to read Input file");
		}
		try {
			songService.save(songTitle, userName, songFile);
			clientService.publish();
			return APIResponse.toOkResponse("Successfully uploaded Song");
		} catch (Exception e) {
			logger.error(e.getMessage() , e);
			return APIResponse.toErrorResponse("Unable to upload file because of error " + e.getMessage());
		}
	}
	
	@RequestMapping(value="/getAllSongs" , method=RequestMethod.GET)
	public @ResponseBody APIResponse getAllSongs()
	{
		HashMap<String, Object> authResp = new HashMap<String, Object>();
		List<Song> allSongsList = songService.findAllSongs();
		
		authResp.put("songList", allSongsList);
		
		return APIResponse.toOkResponse(authResp);
	}
	
	@RequestMapping(value="/setSongAsPlaying", method=RequestMethod.POST)
	public @ResponseBody APIResponse setSongAsPlaying(@RequestParam(name="songId") long songId)
	{
		songService.setSongAsPlaying(songId);
		return APIResponse.toOkResponse("Succesfully set the song as playing");
	}
}
