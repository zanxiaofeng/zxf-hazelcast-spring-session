package com.zxf.hazelcast.springsession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.hazelcast.spring.session.config.annotation.web.http.EnableHazelcastHttpSession;

@SpringBootApplication
@EnableHazelcastHttpSession(maxInactiveIntervalInSeconds = 120)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
