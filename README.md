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
