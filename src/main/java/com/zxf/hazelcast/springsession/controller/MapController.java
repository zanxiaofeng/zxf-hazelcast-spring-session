package com.zxf.hazelcast.springsession.controller;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.spring.session.AttributeValue;
import com.hazelcast.spring.session.BackingMapSession;
import com.zxf.hazelcast.springsession.bean.MyBean;
import org.openjdk.jol.vm.VM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;

import static com.hazelcast.spring.session.HazelcastIndexedSessionRepository.DEFAULT_SESSION_MAP_NAME;
import static com.zxf.hazelcast.springsession.controller.SessionController.beanAttrName;

@RestController
@RequestMapping("/maps")
public class MapController {
    @Autowired
    private HazelcastInstance hazelcastInstance;

    @GetMapping
    public String list(HttpServletRequest request) {
        return "Maps found: <br>" + hazelcastInstance.getDistributedObjects().stream()
                .filter(d -> d.getServiceName().equals("hz:impl:mapService"))
                .map(d -> d.getName())
                .collect(Collectors.joining("<br>"));
    }

    @GetMapping("/{map}/items")
    public String items(HttpServletRequest request, @PathVariable String map) {
        IMap<String, Object> sessions = hazelcastInstance.getMap(map);
        return "Items found: <br>" + sessions.entrySet().stream().map(this::toString).collect(Collectors.joining("<br>"));
    }

    private String toString(Map.Entry<String, Object> entry) {
        if (entry.getValue() instanceof BackingMapSession session) {
            String principal = session.getPrincipalName();
            AttributeValue beanValue = session.getAttribute(beanAttrName);
            MyBean myBean = beanValue != null ? beanValue.deserialize(hazelcastInstance, MyBean.class) : null;
            Long addr = myBean != null ? VM.current().addressOf(myBean) : null;
            return "key: " + entry.getKey() + ", value: {sessionId=" + session.getId() + ", principal=" + principal + ", bean=" + myBean + ":addr:" + addr + "}";
        }

        return "key: " + entry.getKey() + ", value: " + entry.getValue().toString();
    }
}
