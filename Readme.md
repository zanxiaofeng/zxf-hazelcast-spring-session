# Start
- SERVER_PORT=8080 mvn spring-boot:run
- SERVER_PORT=8081 mvn spring-boot:run

# Test
- http://localhost:8080/sessions/create?principal=davis&foo=123&bar=456
- http://localhost:8080/sessions/info
- http://localhost:8080/sessions/list
