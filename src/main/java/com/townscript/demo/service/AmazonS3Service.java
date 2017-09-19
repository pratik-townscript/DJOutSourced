package com.townscript.demo.service;

import org.springframework.web.multipart.MultipartFile;

public interface AmazonS3Service {
	void addSongToBucket(String fileName , MultipartFile songFile) throws Exception;
}
