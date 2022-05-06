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
- http://127.0.0.1:8081/sessions/info-update?newBar=2344
- http://127.0.0.1:8081/sessions/list/my

# Test Step 2
## session one
- http://localhost:8080/maps
- http://localhost:8080/maps/spring:session:sessions/items

## session tow
- http://127.0.0.1:8081/maps
- http://127.0.0.1:8081/maps/spring:session:sessions/items