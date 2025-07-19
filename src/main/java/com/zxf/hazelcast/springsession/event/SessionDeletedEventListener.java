package com.zxf.hazelcast.springsession.event;

import org.springframework.context.ApplicationListener;
import org.springframework.session.events.SessionDeletedEvent;
import org.springframework.stereotype.Component;

@Component
public class SessionDeletedEventListener implements ApplicationListener<SessionDeletedEvent> {
    @Override
    public void onApplicationEvent(SessionDeletedEvent event) {
        System.out.printf("会话已删除: %s\n", event.getSessionId());
    }
}