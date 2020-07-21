# Community

## 모듈 구조

[멀티모듈 설계 이야기 with Spring, Gradle](https://woowabros.github.io/study/2019/07/01/multi-module.html) 참조

```
독립 모듈 계층: independents/
공통 모듈 계층: core/
도메인 모듈 계층: domain/
내부 모듈 계층: in-system-available/
애플리케이션 모듈 계층: apps/
```

모듈 이름은 `:{layer}:{layer}-{moduleName}` 형태로 짓는다. `:{layer}:{moduleName}` 형태가 되면 모듈을 제대로 찾지 못한다.

예)

```
':core:mother', ':domain:mother'로 이름을 짓는 경우 ':domain:mother'모듈에서 ':core:mother' 모듈을 사용할 수 없음

':core:core-mother', ':domain:domain-mother'일 때는 ':domain:domain-mother'에서 ':core:core-mother' 모듈을 사용할 수 있다.
```

공통 모듈에서 담당하는 것: Dto 및 Dto의 Validation 로직 검증.

## Applications

하나의 도커 이미지에서 4개의 애플리케이션이 실행된다.

1. Nginx (Port: 80)
2. Community Server (Port: 8080)
3. Auth Server (Port: 8081)
4. Frontend (Port: 3000)
5. BackOffice (Port: 3001)

### [Nginx routing](https://gist.github.com/soheilhy/8b94347ff8336d971ad0) 규칙 

1. Api Gateway
2. `/api/**` ->  Community의 `/**`
3. `/api/users/**` Auth의 `/users`
4. `/api/oauth/**` Auth의 `/oauth`
5. `/**` Frontend
6. `/admin/**` BackOffice

모든 request는 Nginx를 통해 전달한다. Nginx는 path에 따라 request를 Community, Auth, Frontend에 나눠준다.

Heatlh check는 Community에서 나머지 애플리케이션의 health를 체크해서 response. 애플리케이션 하나라도 상태가 이상하면 비정상 응답을 내보낸다.

* `me` 요청은 `/api/user-resource/me`로. Auth가 아니라 Community에서 담당한다. 인증에 필요한 정보는 User에, 나머지 사용자 정보는 UserResource에 담는다. Auth에서는 User만 관리함. UserResource는 Community에서.

### Flyway migrate

app-monolith의 `resource/db/migration` 에 모든 SQL 쌓기. app-auth는 symlink로 app-monolith의 `resource/db/migration`을 바로보도록 한다. 

#### local-db
DockerComposeUp으로 mariadb를 띄운 뒤 community database를 생성하고 아래의 migrate 커맨드를 실행해야 함. Port는 3307로 정했습니다.

```bash
# db init
mysql -uroot -psecret -h0.0.0.0 -P3307
create database community;
GRANT ALL PRIVILEGES ON community.* TO 'mariadb'@'%';
```

```bash
# db access
mysql community -umariadb -psecret -h0.0.0.0 -P3307
```

```bash
./gradlew :app:app-monolith:flywayMigrate -Dflyway.url=jdbc:mysql://0.0.0.0:3307/community -Dflyway.user=mariadb -Dflyway.password=secret
```

### auth의 resources의 import.sql과 schema.sql

로컬의 bootRun을 할 때만 사용한다. bootRunLocalDb나 integration test, 그리고 remote 환경에서는 flyway로 db-migrations의 스크립트를 사용. 

### Database

일반 test와 로컬 bootRun에는 h2를 사용한다.

통합 테스트에서는 MariaDB4j를 사용하고 운영 환경에서는 MariaDB를 사용한다.

이게 다 h2에서 `LONG VARBINARY`를 못 알아듣고 `LONGVARBINARY`만 알아듣기 때문이다.
