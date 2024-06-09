package com.rest.rest.events;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class EventControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void createEvent() throws Exception {
        mockMvc.perform(post("/api/events/")
                        .contentType(MediaType.APPLICATION_JSON) // 최신은 APPLICATION_JSON_UTF8 대신 APPLICATION_JSON 사용 권장
                        .accept(MediaTypes.HAL_JSON)) // Hypertext Application Language
                .andExpect(status().is(404)); //201 유도할 거지만 아직 미완성이라 404
    }


}
