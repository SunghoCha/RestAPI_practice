package com.rest.rest.events;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EventTest {

    @Test
    public void builder() {
        Event event = Event.builder()
                .name("이름입니다.")
                .description("REST API develoment")
                .build();
        assertThat(event).isNotNull();
    }

    @Test
    public void javaBean() {
        //given
        Event event = new Event();
        String name = "Event";
        String description = "Spring";

        //when
        event.setName(name);
        event.setDescription(description);

        //then
        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);
    }
}
