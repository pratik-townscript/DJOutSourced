package com.townscript.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="song")
public class Song {

	private Long id;
	private String title;
	private Long upVote = 0l;
	private User user;
	private boolean currentSong;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Long getUpVote() {
		return upVote;
	}
	public void setUpVote(Long upVote) {
		this.upVote = upVote;
	}
	
	@OneToOne
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	@Column(columnDefinition = "TINYINT")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public boolean isCurrentSong() {
		return currentSong;
	}
	public void setCurrentSong(boolean currentSong) {
		this.currentSong = currentSong;
	}
	
	@Override
	public String toString() {
		return "Song [id=" + id + ", title=" + title + ", upVote=" + upVote + ", user=" + user
				+ ", currentSong=" + currentSong + "]";
	}
}
