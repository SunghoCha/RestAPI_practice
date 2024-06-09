package com.rest.rest.util;

import com.rest.rest.events.Event;
import com.rest.rest.events.UpdateEventRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EventMapper {
    EventMapper INSTANCE =  Mappers.getMapper(EventMapper.class);

    // UpdateEventReqeust -> Event 매핑
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "offline", ignore = true)
    @Mapping(target = "free", ignore = true)
    @Mapping(target = "eventStatus", ignore = true)
    Event toEvent(UpdateEventRequest updateEventRequest);
}
