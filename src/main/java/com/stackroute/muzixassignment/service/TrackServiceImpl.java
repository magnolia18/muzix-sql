package com.stackroute.muzixassignment.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.stackroute.muzixassignment.exceptions.TrackAlreadyExistsException;
import com.stackroute.muzixassignment.exceptions.TrackNotFoundException;
import com.stackroute.muzixassignment.model.Track;
import com.stackroute.muzixassignment.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class TrackServiceImpl implements TrackService {
    TrackRepository trackRepository;
    @Autowired
    public TrackServiceImpl(TrackRepository trackRepository)
    {
        this.trackRepository=trackRepository;
    }
    @Override
    public Track saveTrack(Track track) throws TrackAlreadyExistsException {
        if(trackRepository.existsById(track.getId()))
        {
            throw new TrackAlreadyExistsException("user already exists");
        }
        Track savedTrack=trackRepository.save(track);
        if(savedTrack==null)
        {
            throw new TrackAlreadyExistsException("user alraedy exists");
        }
        return savedTrack;

    }


    @Override
    public List<Track> getAllTrack() {
        return trackRepository.findAll();
    }

    @Override
    public boolean deleteTrack(int id) throws TrackNotFoundException {
        Optional<Track> track1 = trackRepository.findById(id);

        if(!track1.isPresent())
        {
            throw new TrackNotFoundException("User Not Found");
        }

        try {

            trackRepository.delete(track1.get());

            return true;

        }
        catch (Exception exception)
        {
            return false;
        }
    }

    @Override
    public List<Track> getTrackByName(String firstName) throws TrackNotFoundException {
        List<Track> track=trackRepository.getUserByName(firstName);
        if(track.isEmpty())
        {
            throw new TrackNotFoundException("User not found");
        }

        return trackRepository.getUserByName(firstName);
    }

    @Override
    public Track updateTrack(int id,Track track) {
        Track updateTrack=trackRepository.save(track);
        return updateTrack;
    }

    @Override
    public void fetchTrackData() {
        final String url = "http://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key=b361cf9c3552a82a0d4078248d1e31d2&format=json";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;

        try {
            jsonNode =  (JsonNode) objectMapper.readTree(result.getBody());
            JsonNode arrayNode = jsonNode.path("tracks").path("tracks");

            for(int i=0; i<arrayNode.size();i++)
            {
                Track track = new Track();
                track.setName(arrayNode.get(i).path("name").asText());
                track.setComments((arrayNode.get(i).path("comments").asText()));
                trackRepository.save(track);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
