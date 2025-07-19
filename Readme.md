# Start
- SERVER_PORT=8080 mvn spring-boot:run
- SERVER_PORT=8081 mvn spring-boot:run

# Test Step 1
## session one(cookie domain=localhost)
- http://localhost:8080/sessions/create?principal=davis&foo=123&bar=456
- http://localhost:8080/sessions/info
- http://localhost:8080/sessions/list/my
- http://localhost:8080/sessions/info-update?newBar=2344
- http://localhost:8080/sessions/list/my

## session tow(cookie domain=127.0.0.1)
- http://127.0.0.1:8081/sessions/create?principal=ben&foo=123&bar=456
- http://127.0.0.1:8081/sessions/info
- http://127.0.0.1:8081/sessions/list/my
- http://127.0.0.1:8081/sessions/info-update?newBar=2344&save=true
- http://127.0.0.1:8081/sessions/list/my

# Test Step 2
## session one
- http://localhost:8080/maps
- http://localhost:8080/maps/spring:session:sessions/items

## session tow
- http://127.0.0.1:8081/maps
- http://127.0.0.1:8081/maps/spring:session:sessions/items

# Test Cache
- http://localhost:8080/caches/one?id=1&name=a
- http://localhost:8080/caches/one-by-custom?id=2&name=b
- http://localhost:8080/maps
- http://localhost:8080/maps/default-cache/items
- http://localhost:8080/maps/custom-cache/items

# Spring auto configuration
- spring-boot-autoconfigure-2.7.18.jar!/META-INF/spring.factories
- spring-boot-autoconfigure-2.7.18.jar!/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
- org.springframework.boot.autoconfigure.session.SessionAutoConfiguration

# Servlet Core Classes
- javax.servlet.ServletContext
- javax.servlet.SessionCookieConfig
- javax.servlet.http.HttpFilter
- javax.servlet.http.HttpServlet
- javax.servlet.http.HttpServletRequest
- javax.servlet.http.HttpServletResponse
- javax.servlet.http.HttpSession
- javax.servlet.http.HttpSessionEvent
- javax.servlet.http.HttpSessionListener

# Spring Session Core Classes
- org.springframework.session.config.annotation.web.http.EnableSpringHttpSession@
- org.springframework.session.config.annotation.web.http.SpringHttpSessionConfiguration
- org.springframework.session.config.SessionRepositoryCustomizer
- org.springframework.session.SessionRepository
- org.springframework.session.config.annotation.web.server.EnableSpringWebSession
- org.springframework.session.config.annotation.web.server.SpringWebSessionConfiguration
- org.springframework.session.config.ReactiveSessionRepositoryCustomizer
- org.springframework.session.ReactiveSessionRepository

# Hazelcast Session Core Classes
- org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession
- org.springframework.session.hazelcast.config.annotation.SpringSessionHazelcastInstance
- org.springframework.session.hazelcast.config.annotation.web.http.HazelcastHttpSessionConfiguration
- org.springframework.session.hazelcast.HazelcastSessionSerializer
- org.springframework.session.hazelcast.Hazelcast4IndexedSessionRepository[breakpoint on cotr]
- org.springframework.session.hazelcast.HazelcastIndexedSessionRepository[breakpoint on cotr]
- com.hazelcast.map.listener.MapListener
- com.hazelcast.map.listener.EntryAddedListener
- com.hazelcast.map.listener.EntryEvictedListener
- com.hazelcast.map.listener.EntryRemovedListener
- com.hazelcast.map.listener.EntryExpiredListener

# How to upgrade maven project from jdk8 to jdk 17
## For spring-boot-starter-parent 2 project
- <java.version>17</java.version>
## For other project
- maven.compiler.source
- maven.compiler.target
## For more controls
- Use the maven-compiler-plugin configuration settings.