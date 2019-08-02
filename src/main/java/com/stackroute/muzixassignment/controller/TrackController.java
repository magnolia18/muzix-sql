package com.stackroute.muzixassignment.controller;


import com.stackroute.muzixassignment.exceptions.TrackAlreadyExistsException;
import com.stackroute.muzixassignment.exceptions.TrackNotFoundException;
import com.stackroute.muzixassignment.model.Track;

import com.stackroute.muzixassignment.service.TrackService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//Controller class that handles requests and sends a response

@RestController
@RequestMapping("/api/v1")
@ControllerAdvice(basePackages="com.stackroute.muzixassignment")
@Api(tags = {"Track Controller"})
public class TrackController {
    TrackService trackService;

    @Value("${exceptionmsg}")
    String exp;

    //Autowired to inject the trackService dependency
    @Autowired
    public TrackController(TrackService trackService)
    {
        this.trackService=trackService;
    }

    @PostMapping("trackservice")
    @ExceptionHandler(TrackAlreadyExistsException.class)
    public ResponseEntity<?> saveTrack(@RequestBody Track track)
    {
            ResponseEntity responseEntity;
        try
        {
            trackService.saveTrack(track);
            responseEntity=new ResponseEntity<String>("successfully created",HttpStatus.CREATED);
        }
        catch(TrackAlreadyExistsException ex)
        {
//            responseEntity=new ResponseEntity<String>(exp,HttpStatus.CONFLICT);
            responseEntity=new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);

        }
        return responseEntity;
    }

//    @GetMapping
    @GetMapping("trackservice")
    public ResponseEntity<?> getAllTrack(){
        trackService.fetchTrackData();
        return new ResponseEntity<List<Track>>(trackService.getAllTrack(),HttpStatus.OK);
    }

    @DeleteMapping("/trackservice/{id}")
    public ResponseEntity<?> deleteTrack(@PathVariable(value = "id") int id){
        ResponseEntity responseEntity;
        try
        {
            trackService.deleteTrack(id);
            responseEntity=new ResponseEntity("successfully deleted",HttpStatus.OK);
        }
        catch(Exception ex)
        {
            responseEntity=new ResponseEntity<String>(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }


    @PutMapping("/trackservice/{id}")
    public ResponseEntity<?> updateTrack(@PathVariable int id,@RequestBody Track track)
    {
        ResponseEntity responseEntity;
        try
        {
            trackService.updateTrack(id,track);
            responseEntity=new ResponseEntity("successfully updated",HttpStatus.OK);
        }
        catch(Exception ex)
        {
            responseEntity=new ResponseEntity(ex.getMessage(),HttpStatus.CONFLICT);
        }
        return responseEntity;
    }
    @GetMapping("trackservice/{firstName}")
    @ExceptionHandler(TrackNotFoundException.class)
    public ResponseEntity<?> getTrackByName(@PathVariable String firstName) {

        ResponseEntity responseEntity;

        try {
            responseEntity = new ResponseEntity<List<Track>>(trackService.getTrackByName(firstName), HttpStatus.CREATED);


        } catch (TrackNotFoundException e) {
            responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);

        }
        return responseEntity;
    }

}