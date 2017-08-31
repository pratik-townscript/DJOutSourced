package com.townscript.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.townscript.demo.model.Song;

public interface SongRepository extends JpaRepository<Song , Long>{
}
