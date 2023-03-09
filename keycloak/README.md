# Keycloak

## 1. Docker

### 1-1 [개발환경](https://www.keycloak.org/getting-started/getting-started-docker)
```shell
docker run -d \
-p 7070:8080 \
-e KEYCLOAK_ADMIN=admin \
-e KEYCLOAK_ADMIN_PASSWORD=admin \
--name keycloak \
quay.io/keycloak/keycloak:20.0.5 start-dev
```

### 1-2 keycloak with mysql
- [github](https://github.com/keycloak/keycloak-containers/blob/main/docker-compose-examples/keycloak-mysql.yml)
- `keycloak` service 수정해줘도 DB 연동 안됨
```text
KEYCLOAK_USER : KEYCLOAK_ADMIN
KEYCLOAK_PASSWORD : KEYCLOAK_ADMIN_PASSWORD
command : `start-dev`
```

- [dev.to](https://dev.to/kurybr/keycloak-1901-mysql-dockercompose-como-configurar-3an9)
  - KC_DB: mysql 설정 시 [에러](./doc/docker-compose-mysql-error.md) 발생
  - DB 생성 시 MySQL에는 `CLOB` 타입이 없어 오류 발생
```sql
CREATE TABLE keycloak.PROTOCOL_MAPPER_CONFIG
(
    PROTOCOL_MAPPER_ID VARCHAR(36)  NOT NULL,
    VALUE              CLOB,
    NAME               VARCHAR(255) NOT NULL
)
```

  - MariaDB로 변경하여 [연동](./docker/docker-compose.yml)
```shell
version: "3.8"

services:
  mysql:
    container_name: mysql
    image: mariadb:10
    environment:
      MYSQL_DATABASE: keycloak
      MYSQL_ROOT_PASSWORD: 111111
      MYSQL_USER: keycloak
      MYSQL_PASSWORD: 111111
    ports:
      - "3336:3306"
    command:
      - "--character-set-server=utf8mb4"
      - "--collation-server=utf8mb4_general_ci"
      - "--skip-character-set-client-handshake"
      - "--lower_case_table_names=0"

  keycloak:
    container_name: keycloak-mysql
    image: quay.io/keycloak/keycloak:20.0.5
    environment:
      KEYCLOAK_ADMIN: admin
      KEYCLOAK_ADMIN_PASSWORD: admin
      KC_DB: mariadb
      KC_DB_USERNAME: keycloak
      KC_DB_PASSWORD: 111111
      KC_DB_URL_HOST: mysql
      KC_DB_URL_PORT: 3336
      KC_DB_SCHEMA: keycloak
    ports:
      - "7070:8080"
    command:
      - "start-dev"
    depends_on:
      - mysql
```

### 1-3 운영환경, [Running Keycloak in a container](https://www.keycloak.org/server/containers#_running_a_standard_keycloak_container)
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
- [keycloak, Docker](https://www.keycloak.org/getting-started/getting-started-docker)
- [baeldung, spring-boot-keycloak](https://www.baeldung.com/spring-boot-keycloak)
- [TutorialsBuddy, Secure Spring Boot REST APIs using Keycloak](https://www.tutorialsbuddy.com/keycloak-secure-spring-boot-rest-api)
