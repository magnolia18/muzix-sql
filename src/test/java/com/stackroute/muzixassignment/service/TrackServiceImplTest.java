package com.stackroute.muzixassignment.service;

import com.stackroute.muzixassignment.exceptions.TrackAlreadyExistsException;
import com.stackroute.muzixassignment.exceptions.TrackNotFoundException;
import com.stackroute.muzixassignment.model.Track;
import com.stackroute.muzixassignment.repository.TrackRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TrackServiceImplTest {


//        TrackService trackService;
        Track track;
        //Create a mock for UserRepository
        @Mock
        TrackRepository trackRepository;

        //Inject the mocks as dependencies into UserServiceImpl
        @InjectMocks
        TrackServiceImpl trackService;
        List<Track> list= null;


        @Before
        public void setUp(){
            //Initialising the mock object
            MockitoAnnotations.initMocks(this);
            track = new Track();
            track.setName("John");
            track.setId(101);
            track.setComments("Jenny");
            list = new ArrayList<>();
            list.add(track);


        }

        @Test
        public void saveUserTestSuccess() throws TrackAlreadyExistsException {

            when(trackRepository.save((Track) any())).thenReturn(track);
            Track savedUser = trackService.saveTrack(track);
            Assert.assertEquals(track,savedUser);

            //verify here verifies that userRepository save method is only called once
            verify(trackRepository,times(1)).save(track);

        }

        @Test(expected = TrackAlreadyExistsException.class)
        public void saveUserTestFailure() throws TrackAlreadyExistsException {
            when(trackRepository.save((Track) any())).thenReturn(null);
            Track savedUser = trackService.saveTrack(track);
            System.out.println("savedUser" + savedUser);
            //Assert.assertEquals(user,savedUser);

       /*doThrow(new UserAlreadyExistException()).when(userRepository).findById(eq(101));
       userService.saveUser(user);*/


        }

        @Test
        public void getAllUser(){

            trackRepository.save(track);
            //stubbing the mock to return specific data
            when(trackRepository.findAll()).thenReturn(list);
            List<Track> userlist = trackService.getAllTrack();
            Assert.assertEquals(list,userlist);
        }



    @Test
    public void testUpdateTrack() throws TrackNotFoundException{


        when(trackRepository.existsById(track.getId())).thenReturn(true);
        track.setName("Sidrah");
        Track track1=trackService.updateTrack(107,track);
        when(trackRepository.save((Track)any())).thenReturn(track1);
        Assert.assertEquals("Sidrah",track1.getName());
    }

    @Test(expected = TrackNotFoundException.class)
    public void testUpdateTrackFailure() throws TrackNotFoundException{

        when(trackRepository.findById(track.getId())).thenReturn(Optional.empty());
        track.setName("exception case");
        Track track1=trackService.updateTrack(107,track);
    }


    @Test
    public void deleteTrackTest()
    {
        Track track=new Track(57,"DeleteTrack","Deleted");
        trackRepository.delete(track);
        boolean result=trackRepository.existsById(57);
        Assert.assertEquals(false,result);

    }
    }