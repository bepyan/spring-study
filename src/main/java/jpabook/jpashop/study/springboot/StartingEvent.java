package jpabook.jpashop.study.springboot;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StartingEvent implements ApplicationListener<ApplicationStartingEvent> {
    // applcation context가 없으면 실행이 안된다
    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        System.out.println("===================");
        System.out.println("application starting event");
        System.out.println("===================");
    }
}
