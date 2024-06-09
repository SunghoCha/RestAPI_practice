package com.rest.rest.events;

import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//  Spring HATEOAS 1.1 버전d에서는 org.springfreamwork.hateoas.mvc.controllerLinkBuilder.linkTo보다 아래방법을 권장
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
@Controller
public class EventController {

    private final EventRepository eventRepository;

    //@Autowired 생성자가 하나만 있고 생성자가 받아올 파라미터가 이미 Bean으로 등록되어 있으면 생략 가능
    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @PostMapping
    public ResponseEntity createEvent(@RequestBody Event event) {
        Event newEvent = this.eventRepository.save(event);
        URI createUri = linkTo(EventController.class).slash(newEvent.getId()).toUri();
        return ResponseEntity.created(createUri).body(event);
       /*
       HTTP 상태 코드 201(Created)와 함께 응답을 생성하는데 사용됨.
       클라이언트에게 새로운 리소스가 생성되었으며, 해당 리소스에 대한 URI가 createUri로 제공된다는 것을 의미
        */
    }
}
