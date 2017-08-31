package com.townscript.demo.service;

import org.springframework.web.multipart.MultipartFile;

public interface SongService {

	void save(String songTitle, String userName, MultipartFile songFile) throws Exception;
}
