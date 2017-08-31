package com.townscript.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.townscript.demo.api.APIResponse;
import com.townscript.demo.service.SongService;

@Controller
public class SongController {
	protected static final String JSON_API_CONTENT_HEADER = "Content-type=application/json";
	
	@Autowired
	private SongService songService;
	
	@RequestMapping(value="/uploadUserSong", method=RequestMethod.POST)
	public @ResponseBody APIResponse uploadSong( @RequestParam("songTitle") String songTitle,
												 @RequestParam("username") String userName,
												 @RequestParam("file") MultipartFile songFile)
	{
		System.out.println("input title is " + songTitle);
		System.out.println("input userName is " + userName);
		System.out.println("input songFile is " + songFile);
		
		if(songFile.isEmpty())
		{
			return APIResponse.toErrorResponse("Unable to read Input file");
		}
		try {
			songService.save(songTitle, userName, songFile);
			return APIResponse.toOkResponse("Successfully uploaded Song");
		} catch (Exception e) {
			return APIResponse.toErrorResponse("Unable to upload file because of error " + e.getMessage());
		}
	}
	
}
