package com.stackroute.muzixassignment.repository;

import com.stackroute.muzixassignment.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface TrackRepository extends JpaRepository<Track,Integer> {
    @Query(value = "SELECT t FROM Track t WHERE t.name = ?1")
    public List<Track> getUserByName(String name);
}
