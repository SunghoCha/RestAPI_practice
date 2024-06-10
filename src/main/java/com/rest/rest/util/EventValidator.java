package com.rest.rest.util;

import com.rest.rest.events.CreateEventRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;

@Component
public class EventValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return CreateEventRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CreateEventRequest createEventRequest = (CreateEventRequest) target;
        // BasePrice 및 MaxPrice 유효성 검사
        if (createEventRequest.getBasePrice() > createEventRequest.getMaxPrice()
                && createEventRequest.getMaxPrice() != 0) {
            errors.rejectValue("basePrice", "wrongValue", "BasePrice is wrong.");
            errors.rejectValue("maxPrice", "wrongValue", "MaxPrice is wrong.");
        }
        // 날짜 및 시간 유효성 검사
        LocalDateTime endEventDateTime = createEventRequest.getEndEventDateTime();
        LocalDateTime beginEventDateTime = createEventRequest.getBeginEventDateTime();
        LocalDateTime closeEnrollmentDateTime = createEventRequest.getCloseEnrollmentDateTime();
        LocalDateTime beginEnrollmentDateTime = createEventRequest.getBeginEnrollmentDateTime();

        if (endEventDateTime.isBefore(beginEventDateTime)
                || endEventDateTime.isBefore(closeEnrollmentDateTime)
                || endEventDateTime.isBefore(beginEnrollmentDateTime)) {
            errors.rejectValue("endEventDateTime", "wrongValue", "endEventDateTime is wrong.");
        }
        // TODO beginEventDataTime
        // TODO CloseEnrollmentDateTime
    }
}
