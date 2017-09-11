package com.townscript.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.townscript.demo.model.UserSongVoteMap;

public interface UserSongUpVoteMapRepository extends JpaRepository<UserSongVoteMap, Long> {
	
	List<UserSongVoteMap> findAllByUserId(Long userId);
	
	void deleteBySongId(Long songId);
}
