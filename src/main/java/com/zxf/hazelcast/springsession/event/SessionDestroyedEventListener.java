package com.zxf.hazelcast.springsession.event;

import org.springframework.context.ApplicationListener;
import org.springframework.session.events.SessionDestroyedEvent;

public class SessionDestroyedEventListener implements ApplicationListener<SessionDestroyedEvent> {
    @Override
    public void onApplicationEvent(SessionDestroyedEvent event) {
        System.out.printf("会话已创建: %s\n", event.getSessionId());
    }
}