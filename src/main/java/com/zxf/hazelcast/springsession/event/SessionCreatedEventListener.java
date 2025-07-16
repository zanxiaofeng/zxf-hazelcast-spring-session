package com.zxf.hazelcast.springsession.event;


import org.springframework.context.ApplicationListener;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public class SessionCreatedEventListener implements ApplicationListener<SessionCreatedEvent> {
    @Override
    public void onApplicationEvent(SessionCreatedEvent event) {
        System.out.printf("会话已创建: %s\n", event.getSessionId());
    }
}