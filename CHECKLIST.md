# 🌿구현 체크 리스트

## 과제 진행 요구 사항

- [ ] 기능을 구현하기 전 `README.md`에 구현할 기능 목록을 정리해 추가
- [ ] Git의 커밋 단위는 앞 단계에서 `README.md`에 정리한 기능 목록 단위로 추가
- [ ] [AngularJS Git Commit Message Conventions](https://gist.github.com/stephenparish/9941e89d80e2bc58a153)을 참고해 커밋
  메시지를 작성

## 기능 요구 사항

- [ ] [기능 목록](#-기능-목록) 참고

## 프로그래밍 요구 사항

### 프로그래밍 요구 사항 1

- [ ] Kotlin 2.2.0에서 실행
- [ ] Java 코드가 아닌 Kotlin 코드로만 구현
- [ ] 프로그램 실행의 시작점은 Application의 main()
- [ ] build.gradle.kts 파일은 변경 금지
    - [ ] 제공된 라이브러리 이외의 외부 라이브러리는 사용 금지
- [ ] 프로그램 종료 시 System.exit() 또는 exitProcess()를 호출하지 않는다.
- [ ] 프로그래밍 요구 사항에서 달리 명시하지 않는 한 파일, 패키지 등의 이름을 바꾸거나 이동하지 않는다.
- [ ] 코틀린 코드 컨벤션을 지키면서 프로그래밍한다.
    - [ ] 기본적으로 [Kotlin Style Guide](https://github.com/woowacourse/woowacourse-docs/tree/main/styleguide/kotlin)를 원칙으로
      한다.

### 프로그래밍 요구 사항 2

- [ ] indent(인덴트, 들여쓰기) depth를 3이 넘지 않도록 구현
- [ ] 함수(또는 메서드)가 한 가지 일만 하도록 최대한 작게 구현
- [ ]  JUnit 5와 AssertJ를 이용하여 정리한 기능 목록이 정상적으로 작동하는지 테스트 코드로 확인

### 프로그래밍 요구 사항 3

- [ ] 함수(또는 메서드)의 길이가 15라인을 넘어가지 않도록 구현한
    - [ ] 함수(또는 메서드)가 한 가지 일만 잘 하도록 구현
- [ ] else를 지양
- [ ] Enum 클래스를 적용하여 프로그램을 구현
- [ ] 구현한 기능에 대한 단위 테스트를 작성
    - 단, UI(System.out, System.in, Scanner) 로직은 제외
- [ ] `camp.nextstep.edu.missionutils`에서 제공하는 `Randoms` 및 `Console API`를 사용하여 구현
    - `camp.nextstep.edu.missionutils.Randoms`의 `pickUniqueNumbersInRange()`를 활용
    - `camp.nextstep.edu.missionutils.Console`의 `readLine()`을 활용
- [ ] 제공된 Lotto 클래스를 사용하여 구현
    - [ ] Lotto에 numbers 이외의 필드(인스턴스 변수)를 추가할 수 없다.
    - [ ] numbers의 접근 제어자인 private은 변경할 수 없다.
    - [ ] Lotto의 패키지를 변경할 수 있다.
