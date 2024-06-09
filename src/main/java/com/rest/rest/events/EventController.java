package com.rest.rest.events;

import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//  Spring HATEOAS 1.1 버전d에서는 org.springfreamwork.hateoas.mvc.controllerLinkBuilder.linkTo보다 아래방법을 권장
import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
@Controller
public class EventController {

    @PostMapping
    public ResponseEntity createEvent(@RequestBody Event event) {
        URI createUri = linkTo(EventController.class).slash("{id}").toUri();
        event.setId(10);
        return ResponseEntity.created(createUri).body(event);
       /*
       HTTP 상태 코드 201(Created)와 함께 응답을 생성하는데 사용됨.
       클라이언트에게 새로운 리소스가 생성되었으며, 해당 리소스에 대한 URI가 createUri로 제공된다는 것을 의미
        */
    }
}
