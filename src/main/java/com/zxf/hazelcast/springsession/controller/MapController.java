package com.zxf.hazelcast.springsession.controller;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.zxf.hazelcast.springsession.bean.MyBean;
import org.openjdk.jol.vm.VM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.MapSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;

import static com.zxf.hazelcast.springsession.controller.SessionController.beanAttrName;
import static com.zxf.hazelcast.springsession.controller.SessionController.principalAttrName;
import static org.springframework.session.hazelcast.Hazelcast4IndexedSessionRepository.DEFAULT_SESSION_MAP_NAME;

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
        if (entry.getValue() instanceof MapSession) {
            MapSession session = (MapSession) entry.getValue();
            String principal = (String) session.getAttribute(principalAttrName);
            MyBean myBean = (MyBean) session.getAttribute(beanAttrName);
            Long addr = VM.current().addressOf(myBean);
            return "key:" + entry.getKey() + ", value:{sessionId=" + session.getId() + ", principal=" + principal + ", bean=" + myBean + ":addr:" + addr + "}";
        }

        return "key:" + entry.getKey() + ", value: " + entry.getValue().toString();
    }
}
