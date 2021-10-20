package com.zxf.hazelcast.springsession.controller;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.zxf.hazelcast.springsession.bean.MyBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.MapSession;
import org.springframework.session.Session;
import org.springframework.session.hazelcast.Hazelcast4IndexedSessionRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.session.hazelcast.Hazelcast4IndexedSessionRepository.DEFAULT_SESSION_MAP_NAME;

@RestController
@RequestMapping("/sessions")
public class SessionController {
    private static final String principalAttrName = Hazelcast4IndexedSessionRepository.PRINCIPAL_NAME_INDEX_NAME;
    private static final String beanAttrName = "bean";

    @Autowired
    private HazelcastInstance hazelcastInstance;

    @Autowired
    Hazelcast4IndexedSessionRepository sessionRepository;

    @GetMapping("/create")
    public String create(HttpServletRequest request, @RequestParam String principal,
                         @RequestParam String foo, @RequestParam String bar) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            return "Session already exists: " + session.getId();
        } else {
            MyBean myBean = new MyBean(foo, bar);
            session = request.getSession();
            session.setAttribute(principalAttrName, principal);
            session.setAttribute(beanAttrName, myBean);
            return "Session created: " + toString(session) + "<br>";
        }
    }

    @GetMapping("/info")
    public String info(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "No session found.";
        } else {
            return "Session found: " + toString(session) + "<br>";
        }
    }

    @GetMapping("/list/my")
    public String listMy(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "No session found.";
        } else {
            String principal = (String) session.getAttribute(principalAttrName);
            Map<String, ? extends Session> sessions = sessionRepository.findByPrincipalName(principal);
            return "Sessions found: <br>" + sessions.values().stream().map(this::toString).collect(Collectors.joining("<br>"));
        }
    }

    @GetMapping("/list/all")
    public String listAll(HttpServletRequest request) {
        IMap<String, MapSession> sessions = hazelcastInstance.getMap(DEFAULT_SESSION_MAP_NAME);
        return "Sessions found: <br>" + sessions.values().stream().map(this::toString).collect(Collectors.joining("<br>"));
    }

    private String toString(HttpSession session) {
        String principal = (String) session.getAttribute(principalAttrName);
        MyBean myBean = (MyBean) session.getAttribute(beanAttrName);
        String time = new Date(session.getCreationTime()).toString();
        return "sessionId=" + session.getId() + ", time=" + time + ", principal=" + principal + ", bean=" + myBean;
    }

    private String toString(Session session) {
        String principal = (String) session.getAttribute(principalAttrName);
        MyBean myBean = (MyBean) session.getAttribute(beanAttrName);
        return "sessionId=" + session.getId() + ", principal=" + principal + ", bean=" + myBean;
    }

    private String toString(MapSession session) {
        String principal = (String) session.getAttribute(principalAttrName);
        MyBean myBean = (MyBean) session.getAttribute(beanAttrName);
        return "sessionId=" + session.getId() + ", principal=" + principal + ", bean=" + myBean;
    }
}
