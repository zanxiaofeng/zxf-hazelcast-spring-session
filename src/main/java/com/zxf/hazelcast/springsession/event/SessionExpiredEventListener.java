package com.zxf.hazelcast.springsession.event;

import org.springframework.context.ApplicationListener;
import org.springframework.session.events.SessionExpiredEvent;
import org.springframework.stereotype.Component;

@Component
public class SessionExpiredEventListener implements ApplicationListener<SessionExpiredEvent> {
    @Override
    public void onApplicationEvent(SessionExpiredEvent event) {
        System.out.printf("会话已过期: %s\n", event.getSessionId());
    }
}