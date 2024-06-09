package com.rest.rest.events;

import com.rest.rest.util.EventMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class EventService {

    private final EventRepository eventRepository;

    //@Autowired 생성자가 하나만 있고 생성자가 받아올 파라미터가 이미 Bean으로 등록되어 있으면 생략 가능
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional
    public Event save(CreateEventRequest request) {
        Event event = EventMapper.INSTANCE.toEvent(request); // EventMapper를 사용하여 매핑
        log.info("event : {}", event);
        return eventRepository.save(event);// update와 query를 동시에 하는 느낌.. id 정도만 반환해주는게 맞을지도
    }
}
