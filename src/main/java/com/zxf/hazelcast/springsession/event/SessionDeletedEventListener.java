package com.zxf.hazelcast.springsession.event;

import org.springframework.context.ApplicationListener;
import org.springframework.session.events.SessionDeletedEvent;

public class SessionDeletedEventListener implements ApplicationListener<SessionDeletedEvent> {
    @Override
    public void onApplicationEvent(SessionDeletedEvent event) {
        System.out.printf("会话已销毁: %s\n", event.getSessionId());
    }
}