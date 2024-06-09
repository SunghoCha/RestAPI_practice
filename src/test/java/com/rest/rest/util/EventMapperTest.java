package com.rest.rest.util;

import com.rest.rest.events.CreateEventRequest;
import com.rest.rest.events.Event;
import org.junit.Test;

import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
public class EventMapperTest {

    @Test
    public void testToEvent() {
        //given
        CreateEventRequest request = CreateEventRequest.builder()
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
        //when
        Event event = EventMapper.INSTANCE.toEvent(request);
        //then
        assertThat(request.getName()).isEqualTo(event.getName());
        assertThat(request.getDescription()).isEqualTo(event.getDescription());
        assertThat(request.getBeginEnrollmentDateTime()).isEqualTo(event.getBeginEnrollmentDateTime());
        assertThat(request.getCloseEnrollmentDateTime()).isEqualTo(event.getCloseEnrollmentDateTime());
        assertThat(request.getBeginEventDateTime()).isEqualTo(event.getBeginEventDateTime());
        assertThat(request.getEndEventDateTime()).isEqualTo(event.getEndEventDateTime());
        assertThat(request.getBasePrice()).isEqualTo(event.getBasePrice());
        assertThat(request.getMaxPrice()).isEqualTo(event.getMaxPrice());
        assertThat(request.getLimitOfEnrollment()).isEqualTo(event.getLimitOfEnrollment());
        assertThat(request.getLocation()).isEqualTo(event.getLocation());
    }
}
