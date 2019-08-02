package com.stackroute.muzixassignment.controller;
        import com.fasterxml.jackson.databind.ObjectMapper;
        import com.stackroute.muzixassignment.model.Track;
        import com.stackroute.muzixassignment.exceptions.TrackAlreadyExistsException;
        import com.stackroute.muzixassignment.service.TrackService;
        import org.junit.Before;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.mockito.InjectMocks;
        import org.mockito.MockitoAnnotations;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
        import org.springframework.boot.test.mock.mockito.MockBean;
        import org.springframework.http.MediaType;
        import org.springframework.test.context.junit4.SpringRunner;
        import org.springframework.test.web.servlet.MockMvc;
        import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
        import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
        import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
        import org.springframework.test.web.servlet.setup.MockMvcBuilders;
        import java.util.ArrayList;
        import java.util.List;

        import static org.mockito.ArgumentMatchers.*;
        import static org.mockito.Mockito.when;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class TrackControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private Track track;
    @MockBean
    private TrackService trackService;
    @InjectMocks
    private TrackController trackController;

    private List<Track> list =null;

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(trackController).build();
        track = new Track();
        track.setId(101);
        track.setName("Zyan");
        track.setComments("Awesome");
        list = new ArrayList();
        list.add(track);
    }

    @Test
    public void saveTrack() throws Exception {
        when(trackService.saveTrack(any())).thenReturn(track);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/track")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(track)))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());


    }
    @Test
    public void saveTrackFailure() throws Exception {
        when(trackService.saveTrack(any())).thenThrow(TrackAlreadyExistsException.class);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/track")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(track)))
                .andExpect(status().isConflict())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getAllUser() throws Exception {
        when(trackService.getAllTrack()).thenReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/track")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(track)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

@Test
public void updateTrack() throws Exception {
    Track track1 = new Track(1,"testcase","test comment");
    when(trackService.updateTrack(anyInt(), any())).thenReturn(track1);
    mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/track/1")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(asJsonString(track)))
            .andExpect(status().isOk());
}
    @Test
    public void deleteTrack() throws Exception {
        when(trackService.deleteTrack(anyInt())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/track/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(track)))
                .andExpect(status().isOk());
    }
    private static String asJsonString(final Object obj)
    {
        try{
            return new ObjectMapper().writeValueAsString(obj);

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}