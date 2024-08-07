package com.rest.rest.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTests { 
    // 컨트롤러 테스트인데 어쩌다보니 db연결까지 하게되고 중간에 service단까지 넣으면서 클라이언트 ~ db 까지 이어지는 통합테스트가 되어버림..
    // 컨트롤러 단에서 연결정도만 확인하는 작은 규모의 통합테스트로 유지해야겠음

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("입력값들을 전달하면 JSON 응답으로 201이 온다. " +
            "Location 헤더에 생성된 이벤트를 조회할 수 있는 URI가 담겨 있다." +
            "id는 DB에 들어갈 때 자동생선된 값으로 나온다.")
    public void createEvent() throws Exception {
        //given
        CreateEventRequest createEventRequest = CreateEventRequest.builder()
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 23, 14, 21))
                .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 24, 14, 21))
                .beginEventDateTime(LocalDateTime.of(2018, 11, 25, 14, 21))
                .endEventDateTime(LocalDateTime.of(2018, 11, 26, 14, 21))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 인프런 회사")
                .build();
        //expected
        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON) // 최신은 APPLICATION_JSON_UTF8 대신 APPLICATION_JSON 사용 권장
                        .accept(MediaTypes.HAL_JSON) // Hypertext Application Language
                        .content(objectMapper.writeValueAsString(createEventRequest)))
                .andExpect(status().is(201))
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("id").value(Matchers.not(100)))
                .andExpect(jsonPath("name").value(Matchers.equalTo("Spring")))
                .andDo(print()); // Do(print())에 나오는 모든 내용들은 andExpect를 통해서 검증할 수 있는 내용들
    }

    @Test
    @DisplayName("CreateEventRequest 대신 Event에 해당하는 데이터가 넘어옴" +
            "입렦값으로 원하는 스펙 이외의 다른 데이터까지 같이 넘어왔을때 어떻게 처리할 것인지" +
            "Bad_Request? OR 받기로 한 값 이외는 무시")
    public void createEvent_Bad_Request() throws Exception{
            //given
            Event event = Event.builder()
                    .id(100)
                    .name("Spring")
                    .description("REST API Development with Spring")
                    .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 23, 14, 21))
                    .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 24, 14, 21))
                    .beginEventDateTime(LocalDateTime.of(2018,11, 25, 14, 21))
                    .endEventDateTime(LocalDateTime.of(2018, 11, 26, 14, 21))
                    .basePrice(100)
                    .maxPrice(200)
                    .limitOfEnrollment(100)
                    .location("강남역 인프런 회사")
                    .free(true)
                    .offline(true)
                    .eventStatus(EventStatus.PUBLISHED)
                    .build();
            //expected
            mockMvc.perform(post("/api/events")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaTypes.HAL_JSON)
                            .content(objectMapper.writeValueAsString(event))) // UpdateEventRequest대신 Event 넣어봄 (원하는 필드값이 들어오는 경우 어떻게 처리할건지?)
                    .andExpect(status().isBadRequest())
                    .andDo(print());
    }

    @Test
    @DisplayName("데이터로 null이 들어오면 BadRequest")
    public void createEvent_Bad_Request_Empty_Input() throws Exception {
        //given
        CreateEventRequest createEventRequest = CreateEventRequest.builder().build();
        //expected
        this.mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        //.accept(MediaTypes.HAL_JSON)
                        .content(this.objectMapper.writeValueAsString(createEventRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].objectName").exists());
    }

    @Test
    @DisplayName("유효하지않은 범위의 데이터가 들어오면 BadRequest")
    public void createEvent_Bad_Request_Wrong_Input() throws Exception {
        //given
        CreateEventRequest createEventRequest = CreateEventRequest.builder()
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 23, 14, 21))
                .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 24, 14, 21))
                .beginEventDateTime(LocalDateTime.of(2018, 11, 29, 14, 21))
                .endEventDateTime(LocalDateTime.of(2018, 11, 26, 14, 21))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 인프런 회사")
                .build();

        this.mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(createEventRequest)))
                .andExpect(status().isBadRequest());
    }
}
