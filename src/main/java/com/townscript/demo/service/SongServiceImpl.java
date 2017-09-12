package com.townscript.demo.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.townscript.demo.model.Song;
import com.townscript.demo.model.User;
import com.townscript.demo.repository.SongRepository;
import com.townscript.demo.repository.UserRepository;

@Service
public class SongServiceImpl implements SongService{

	@Autowired private UserRepository userRepository;
	@Autowired private SongRepository songRepository;
	
	public void save(String songTitle, String userName, MultipartFile songFile) throws Exception {
		String rootPath = System.getProperty("catalina.home");
		System.out.println("root path is " + rootPath);
		
		String originalFileName = songFile.getOriginalFilename();
		System.out.println("input songFile name is " + originalFileName);
		
		byte[] bytes = songFile.getBytes();

		File dir = new File(rootPath + File.separator + "songsFolder");
		if(!dir.exists())
			dir.mkdirs();
		
		String newFileName = getNewFileName(originalFileName, dir);
		
		File songFileTemp = new File(dir.getAbsolutePath() + File.separator + newFileName);
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(songFileTemp));
		stream.write(bytes);
		stream.close();
		
		User user = userRepository.findByUsername(userName);
		Song song = new Song();
		song.setPath(newFileName);
		song.setTitle(songTitle);
		song.setUser(user);
		
		System.out.println("Saving song in db");
		songRepository.save(song);
	}

	public List<Song> findAllSongs() {
		
		//String rootPath = System.getProperty("catalina.home");
		//File dir = new File(rootPath + File.separator + "songsFolder");
		
		List<Song> songList = songRepository.findAll();
		/*if(songList != null && !songList.isEmpty()) {
			for(Song song : songList) {
				song.setPath(dir.getAbsolutePath() + File.separator + song.getPath());
			}
		}*/
		return songList;
	}
	
	public void setSongAsPlaying(long songId) {
		
		System.out.println("setting as currently playing " + songId);
		
		Song song = songRepository.findOne(songId);
		
		System.out.println("song retrieved is " + song);
		
		song.setCurrentSong(true);
		songRepository.save(song);
	}
	
	private String getNewFileName(String fileName , File songDir)
	{
		String fNameBeforeExtn = fileName.substring(0 , fileName.lastIndexOf("."));
		System.out.println("fname is " + fNameBeforeExtn);
		
		String fExtn = fileName.substring(fileName.lastIndexOf(".") + 1);
		System.out.println("fname is after etx " + fExtn);
		
		final Pattern p = Pattern.compile(fNameBeforeExtn);
		
		
		File [] sameFileList = songDir.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return p.matcher(pathname.getName()).find();
			}
		});
		int sequenceCount = sameFileList.length;
		String newFileName = fileName;
		if(sequenceCount > 0)
		{
			newFileName = fNameBeforeExtn + "(" + sequenceCount + ")" + "." + fExtn;
		}
		System.out.println("new file name is " + newFileName);
		return newFileName;
	}
}
