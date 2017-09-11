package com.townscript.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_song_map")
public class UserSongVoteMap {
	
	private Long id;
	private Long userId;
	private Long songId;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "user_id")
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	@Column(name = "song_id")
	public Long getSongId() {
		return songId;
	}
	public void setSongId(Long songId) {
		this.songId = songId;
	}
	
}
