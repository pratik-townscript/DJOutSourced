package com.townscript.demo.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class AmazonServiceImpl implements AmazonS3Service{
	private static final Logger logger = Logger.getLogger(AmazonServiceImpl.class);
	
	public static String ACCESS_KEY_ID = "";
	public static String ACCESS_PASSWORD = "";
	public static String BUCKET_NAME = "";
	public static String FOLDER_NAME = "";
	
	private AmazonS3 s3Client;
	
	public AmazonServiceImpl(){
		init();
	}
	
	private void init() {
		logger.info("Initiating Connection to Amazon S3 webservice");
		AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY_ID, ACCESS_PASSWORD);
		s3Client = new AmazonS3Client(credentials);
	}

	public void addSongToBucket(String fileName , MultipartFile songFile) throws Exception
	{
		try 
		{
			logger.info("Adding song file with name " + fileName);
			PutObjectRequest objectRequest = new PutObjectRequest(BUCKET_NAME, 
																  FOLDER_NAME + "/" + fileName,  
																  songFile.getInputStream(), 
																  getObjectMetadata(songFile));
			objectRequest =  objectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
			
			s3Client.putObject(objectRequest);
			
		} catch (AmazonServiceException e) {
			logger.error(e.getMessage(), e);
			throw new Exception("Error while connecting to amazonservice", e);
		} catch (AmazonClientException e) {
			logger.error(e.getMessage(), e);
			throw new Exception("Error in Amazon Client", e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new Exception("Error while input file", e);
		}
	}
	
	private ObjectMetadata getObjectMetadata(MultipartFile songFile) throws NoSuchAlgorithmException, IOException
	{
		byte [] inpBytes = songFile.getBytes();
		ObjectMetadata metaData = new ObjectMetadata();
		metaData.setContentLength(inpBytes.length);
		return metaData;
	}
}
