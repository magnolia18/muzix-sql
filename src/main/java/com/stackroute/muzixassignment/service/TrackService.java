package com.stackroute.muzixassignment.service;

import com.stackroute.muzixassignment.exceptions.TrackAlreadyExistsException;
import com.stackroute.muzixassignment.exceptions.TrackNotFoundException;
import com.stackroute.muzixassignment.model.Track;
import org.springframework.http.ResponseEntity;


import java.util.List;

public interface TrackService {
    public Track saveTrack(Track track) throws TrackAlreadyExistsException;
    public List<Track> getAllTrack();
    public boolean deleteTrack(int id) throws TrackNotFoundException;
    public List<Track> getTrackByName(String firstName) throws TrackNotFoundException;
    public Track updateTrack(int id,Track track);
    public void fetchTrackData();
}

