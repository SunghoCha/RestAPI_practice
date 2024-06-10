package com.rest.rest.util;

import com.rest.rest.events.CreateEventRequest;
import com.rest.rest.events.Event;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-10T15:22:05+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
public class EventMapperImpl implements EventMapper {

    @Override
    public Event toEvent(CreateEventRequest createEventRequest) {
        if ( createEventRequest == null ) {
            return null;
        }

        Event.EventBuilder event = Event.builder();

        event.name( createEventRequest.getName() );
        event.description( createEventRequest.getDescription() );
        event.beginEnrollmentDateTime( createEventRequest.getBeginEnrollmentDateTime() );
        event.closeEnrollmentDateTime( createEventRequest.getCloseEnrollmentDateTime() );
        event.beginEventDateTime( createEventRequest.getBeginEventDateTime() );
        event.endEventDateTime( createEventRequest.getEndEventDateTime() );
        event.location( createEventRequest.getLocation() );
        event.basePrice( createEventRequest.getBasePrice() );
        event.maxPrice( createEventRequest.getMaxPrice() );
        event.limitOfEnrollment( createEventRequest.getLimitOfEnrollment() );

        return event.build();
    }
}
