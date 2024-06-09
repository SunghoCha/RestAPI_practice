package com.rest.rest.events;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter // 자바 Bean 규약에 따라 Setter도 추가(쓰고싶지 않은...)
@EqualsAndHashCode(of = "id") // 모든 field를 사용할 경우 연관관계에서 상호참조로 인한 stack over flow 발생
@Entity
public class Event {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime beginEnrollmentDateTime;
    private LocalDateTime closeEnrollmentDateTime;
    private LocalDateTime beginEventDateTime;
    private LocalDateTime endEventDateTime;
    private String location; // (optional) 이게 없으면 온라인 모임
    private int basePrice; // (optional)
    private int maxPrice; // (optional)
    private int limitOfEnrollment;
    private boolean offline;
    private boolean free;
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;

    // ModelMapper는 리플렉션 이슈
    // MapStruct가 이런면에서 더 좋으나 상황에 따라 dto-entity간 모양이 많이 다를 수 있고 문제가 있을 경우 바로 알아차리기 어려운 단점있다고 함
    // builder 패턴을 entity가 build 하지 않은 채로 리턴해주는 방식도 있지만 일단은 가장 일반적인 builder 사용방법 채택
    // builder로 entity를 생성할 때 @NotNull과 같은 조건들이 무시되고 null이 넘어가는 문제가 있어서 그냥 생성자로 바로해야하나?
    // entity가 dto에 의존하는 안좋은 방법... dto의 잦은 변경에 같이 영향받음..
    // 그런데 특정 필드값들을 일일이 받는 것도 결국 dto의 변경의 이유가 같은거 아닌가?
    public void edit(UpdateEventRequest request) {
        this.name = request.getName();
        this.description = request.getDescription();
        this.beginEnrollmentDateTime = request.getBeginEnrollmentDateTime();
        this.closeEnrollmentDateTime = request.getCloseEnrollmentDateTime();
        this.beginEventDateTime = request.getBeginEventDateTime();
        this.endEventDateTime = request.getEndEventDateTime();
        this.location = request.getLocation();
        this.basePrice = request.getBasePrice();
        this.maxPrice = request.getMaxPrice();
        this.limitOfEnrollment = request.getLimitOfEnrollment();
    }
}
