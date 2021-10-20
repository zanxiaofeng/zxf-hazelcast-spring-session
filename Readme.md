# Start
- SERVER_PORT=8080 mvn spring-boot:run
- SERVER_PORT=8081 mvn spring-boot:run

# Test
## session one
- http://localhost:8080/sessions/create?principal=davis&foo=123&bar=456
- http://localhost:8080/sessions/info
- http://localhost:8080/sessions/list/my

## session tow
- http://localhost:8081/sessions/create?principal=ben&foo=123&bar=456
- http://localhost:8081/sessions/info
- http://localhost:8081/sessions/list/my

## session three
- http://localhost:8081/sessions/list/all