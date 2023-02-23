# Keycloak

## Docker

### [개발환경](https://www.keycloak.org/getting-started/getting-started-docker)
```shell
docker run -d \
-p 7070:8080 \
-e KEYCLOAK_ADMIN=admin \
-e KEYCLOAK_ADMIN_PASSWORD=admin \
--name keycloak \
quay.io/keycloak/keycloak:20.0.5 start-dev
```

### 운영환경, [Running Keycloak in a container](https://www.keycloak.org/server/containers#_running_a_standard_keycloak_container)
```shell
docker run --name mykeycloak -p 8080:8080 \
        -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=change_me \
        quay.io/keycloak/keycloak:latest \
        start \
        --db=postgres --features=token-exchange \
        --db-url=<JDBC-URL> --db-username=<DB-USER> --db-password=<DB-PASSWORD> \
        --https-key-store-file=<file> --https-key-store-password=<password>
```

## 참조
- [baeldung, spring-boot-keycloak](https://www.baeldung.com/spring-boot-keycloak)
- [TutorialsBuddy, Secure Spring Boot REST APIs using Keycloak](https://www.tutorialsbuddy.com/keycloak-secure-spring-boot-rest-api)
