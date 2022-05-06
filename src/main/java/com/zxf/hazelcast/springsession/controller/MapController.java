package com.zxf.hazelcast.springsession.controller;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.zxf.hazelcast.springsession.bean.MyBean;
import org.openjdk.jol.vm.VM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.MapSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;

import static com.zxf.hazelcast.springsession.controller.SessionController.beanAttrName;
import static com.zxf.hazelcast.springsession.controller.SessionController.principalAttrName;
import static org.springframework.session.hazelcast.Hazelcast4IndexedSessionRepository.DEFAULT_SESSION_MAP_NAME;

@RestController
@RequestMapping("/map")
public class MapController {
    @Autowired
    private HazelcastInstance hazelcastInstance;

    @GetMapping("/items")
    public String items(HttpServletRequest request) {
        IMap<String, MapSession> sessions = hazelcastInstance.getMap(DEFAULT_SESSION_MAP_NAME);
        return "Sessions found: <br>" + sessions.entrySet().stream().map(this::toString).collect(Collectors.joining("<br>"));
    }

    private String toString(Map.Entry<String, MapSession> entry) {
        String principal = (String) entry.getValue().getAttribute(principalAttrName);
        MyBean myBean = (MyBean) entry.getValue().getAttribute(beanAttrName);
        Long addr = VM.current().addressOf(myBean);
        return "key:" + entry.getKey() + ", value:{sessionId=" + entry.getValue().getId() + ", principal=" + principal + ", bean=" + myBean + ":addr:" + addr + "}";
    }
}
