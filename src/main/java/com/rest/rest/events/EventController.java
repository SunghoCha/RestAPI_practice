package com.rest.rest.events;

import com.rest.rest.util.EventValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Slf4j
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
@RestController
public class EventController {

    private final EventService eventService;
    private final EventValidator eventValidator;
    //@Autowired 생성자가 하나만 있고 생성자가 받아올 파라미터가 이미 Bean으로 등록되어 있으면 생략 가능
    public EventController(EventService eventService, EventValidator eventValidator) {
        this.eventService = eventService;
        this.eventValidator = eventValidator;
    }

    /**
     * WebDataBinder는 스프링의 파라미터 바인딩의 역할을 해주고 검증 기능도 내부에 포함한다
     * @InitBinder에 검증기를 추가하면 해당 컨트롤러에서는 검증기를 자동으로 적용할 수 있다.
     * 해당 컨트롤러에만 영향을 주므로 글로벌 설정은 별도로 해야한다
     *
     *  (나중에 추가할 @ControllerAdvice는 대상으로 지정한 여러 컨트롤러에 @ExceptionHandler, @InitBinder을 부여해주는 역할을 한다)
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(eventValidator);
    }

    @PostMapping
    public ResponseEntity createEvent(@RequestBody @Validated CreateEventRequest createEventRequest, BindingResult bindingResult) {
      // eventValidator.validate(createEventRequest, bindingResult); //eventValidator가 스프링의 Validator를 구현했기 때문에 없어도 자동으로 실행해줌
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();

            return ResponseEntity.badRequest().body(errors);
        }
        log.info("UpdateEventRequest : {}", createEventRequest);
        Event updatedEvent = eventService.save(createEventRequest); // 업데이트 후 객체 반환
        URI createUri = linkTo(EventController.class).slash(updatedEvent.getId()).toUri(); // 해당 리소스에 대한 접근 uri 생성
        return ResponseEntity.created(createUri).body(updatedEvent); // 리소스 접근 uri를 헤더로 제공, 리소스 내용은 바디로 제공

       /*
       HTTP 상태 코드 201(Created)와 함께 응답을 생성하는데 사용됨.
       클라이언트에게 새로운 리소스가 생성되었으며, 해당 리소스에 대한 URI가 createUri로 제공된다는 것을 의미
        */
    }
}
