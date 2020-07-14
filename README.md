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

공통 모듈에서 담당하는 것: Dto 및 Dto의 Validation 로직 검증.
