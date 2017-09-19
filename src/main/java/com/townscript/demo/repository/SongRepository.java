package com.townscript.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.townscript.demo.model.Song;
import java.util.List;

public interface SongRepository extends JpaRepository<Song , Long>{
	 List<Song> findAllOrderById();
}
