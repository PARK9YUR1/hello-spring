# 스프링 입문

## 1️⃣ 프로젝트 환경설정
### 01️ 프로젝트 생성
- Java 17
- IntelliJ

![Alt text](./img/image.png)
https://start.spring.io

**Project Metadata**
- Group : 기업 도메인명
- Artifact : build에 나올 때 결과물

**Dependencies**
- Thymeleaf : HTML을 만들어주는 템플릿 엔진

<br>

IntelliJ에서 `build.gradle` → `Open as Project`

<img src="./img/image2.png" width="140" height="200"/>

- `.idea` : IntelliJ가 사용하는 설정 파일
- `gradle`
- `src`
    - `main`
        - `java` : 실제 패키지, 소스 파일
        - `resources` : Java 코드 파일 제외한 나머지 파일
    - `test` : 테스트 코드
- `build.gradle` : 중요!
    - mavenCentral() : 라이브러리를 받는 사이트

<br>

**HelloSpringApplication 실행**
- `Tomcat initialized with port 8080 (http)` → `localhost:8080`

<br>

Settings → gradle 검색 → `Build and run using` & `Run tests using` : `IntelliJ IDEA`

<br>

### 02 라이브러리 살펴보기

<img src="./img/image3.png" width="200" height="20"/>
External Libraries : 불러온 라이브러리들

<br>

gradle, Maven 같은 툴들은 의존관계를 다 관리해줌. (필요한 것 다 불러옴.)

<img src="./img/image4.png" width="300" height="90"/>

Dependencies : 라이브러리 간의 의존관계

<br>

**logging**
- `slf4j` : 쉽게 말해 인터페이스.
- 요즘엔 실제 로그를 어떤 구현체로 출력하느냐 → `logback`
- starter-logging 라이브러리를 불러오면 slf4j, logback 두 라이브러리도 자동으로 불러옴.

<br>

테스트할 때, JUnit 라이브러리 사용

<br>

| **정리**

**스프링부트 라이브러리**
- spring-boot-starter-web
    - spring-boot-starter-tomcat : 톰캣(웹서버)
    - spring-webmvc : 스프링 웹 MVC
- spring-boot-starter-thymeleaf : 타임리프 템플릿 엔진(View)
- spring-boot-starter(공통) : 스프링 부트 + 스프링 코어 + 로깅
    - spring-boot
        - spring-core
    - spring-boot-starter-logging
        -logback, slf4j

**테스트 라이브러리**
- spring-boot-starter-test
    - junit : 테스트 프레임워크
    - mockito : 목 라이브러리
    - assertj : 테스트 코드를 좀 더 편하게 작성하게 도와주는 라이브러리
    - spring-test : 스프링 통합 테스트 지원

<br>

### 03 View 환경설정

**스프링 필요한 기능 찾기!**
![Alt text](./img/image5.png)
- `Welcome 페이지 찾기` : spring.io 에서 Projects > Spring Boot > LEARN > Documentation > Reference Doc. > Web > 1.1.6. Welcome Page

<br>

**thymeleaf 템플릿 엔진**<br>
템플릿 엔진을 쓰면 정적 페이지를 원하는 대로 바꿀 수 있음.<br>
(thymeleaf라는 템플릿 엔진 사용)
- 공식 사이트 : https://www.thymeleaf.org/
- 스프링 공식 튜토리얼 : https://spring.io/guides/gs/serving-web-content/
- 스프링부트 메뉴얼 : https://docs.spring.io/spring-boot/docs/2.3.1.RELEASE/reference/html/spring-boot-features.html#boot-features-spring-mvc-template-engines (버전..)


```java
// HelloController.java

@Controller
public class HelloController {
    // 웹 어플리케이션에서 /hello 라고 들어오면 이 메소드 호출
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");  // data: hello!!
        return "hello";  // resources/templates/hello.html를 호출하는 것
    }
}
```

```html
<!-- hello.html -->
<body>
<p th:text="'안녕하세요. ' + ${data}">안녕하세요. 손님</p>  <!-- data 값: hello!! -->
</body>
```

<br>

<img src="./img/image6.png" width="350" height="200"/>

- 컨트롤러에서 리턴 값으로 문자를 반환하면 뷰 리졸버(viewResolver)가 화면을 찾아서 처리
    - 스프링 부트 템플릿엔진 기본 viewName 매핑
    - `resources:templates/` + {ViewName} + `.html`

- `spring-boot-devtools` 라이브러리를 추가하면 `html` 파일을 컴파일만 해주면 서버 재시작 없이 View 파일 변경이 가능<br>
    (IntelliJ 컴파일 방법 : 메뉴 build → Recompile)

<br>

### 04 빌드하고 실행하기
콘솔로 이동
1. `gradlew build`
2. `cd build/libs`
3. `java -jar hello-spring-0.0.1-SNAPSHOT.jar`
4. 실행 확인

서버 배포할 때 `java -jar hello-spring-0.0.1-SNAPSHOT.jar` 파일만 복사해서 서버에 넣어 실행하면 됨.

<br>

## 2️⃣ 스프링 웹 개발 기초
웹 개발 3가지 방법
- **정적 컨텐츠** : 파일을 웹브라우저에 그대로 내려줌(서버에서 하는 것 X)
- **MVC와 템플릿 엔진** : 가장 많이 하는 방식. 서버에서 프로그래밍해서 HTML을 동적으로 바꿔서 내려줌.<br>
    `MVC`(모델뷰 컨트롤러) : Model, View, Controller
- **API** : 데이터 구조 포맷으로 클라이언트에게 데이터 전달

### 01 정적 컨텐츠
- 스프링부트 정적 컨텐츠 기능
- https://docs.spring.io/spring-boot/docs/2.3.1.RELEASE/reference/html/spring-boot-features.html#boot-features-spring-mvc-static-content (버전..)

- static 폴더 안에 html 파일 생성 후 `localhost:8080/파일명.html` 열기
- 정적 파일 그대로 반환

<img src="./img/image7.png" width="350" height="200"/>

- 내장 톰캣 서버가 요청을 받고 컨트롤러 쪽에서 html 파일 찾음(컨트롤러 우선순위 가짐), 없으면 내부(static)에서 찾고 반환

<br>

### 02 MVC와 템플릿 엔진
- MVC : Model, View, Controller
    - View : 화면과 관련된 일
    - Controller : 비즈니스 로직.. 서버 뒷단 관련된 일
    - Model : 화면에 넘겨줌

- `^ + P`(ctrl + 6 + P) : 옵션넣기

> Controller
```java
// HelloController.java

@GetMapping("hello-mvc")
public String helloMvc(@RequestParam("name") String name, Model model) {
    model.addAttribute("name", name);
    return "hello-template";
}
```

> View
```html
<!-- hello-template.html -->

<html xmlns:th="http://www.thymeleaf.org">
<body>
<!-- 서버 없이 열면 : hello! empty -->
<!-- 템플릿엔진으로 동작하면 : hello + ${name} (치환) -->
<p th:text="'hello ' + ${name}">hello! empty</p>
</body>
</html>
```

- `localhost:8080/hello-mvc` 접속하면 에러
- `localhost:8080/hello-mvc?name=spring!` 접속 → 화면: hello spring!

<img src="./img/image8.png" width="350" height="200"/>

- helloController에 메서드 맵핑이 되어있으니 스프링에선 메서드 호출
- return : hello-template → model : key는 name, value는 spring!
- viewResolver : 화면과 관련된 해결자. view를 찾고 템플릿을 연결. (hello-template.html 찾음)
- 변환한 html을 넘겨줌.

<br>

### 03 API

**@ResponseBody 사용 원리**

<img src="./img/image9.png" width="350" height="200"/>

```java
// HelloController.java
@GetMapping("hello-api")
@ResponseBody
public Hello helloApi(@RequestParam("name") String name) {  // Hello 라는 객체
    Hello hello = new Hello();
    hello.setName(name);
    return hello;  // 객체를 return
}

// static 클래스로 만들면, 클래스(HelloController) 안에서 클래스(Hello) 사용 가능
static class Hello {
    private String name;
    
    // 프로퍼티 접근 방식
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

`@ResponseBody` 사용
- HTTP의 BODY에 문자 내용을 직접 반환
    - `viewResolver` 대신에 `HttpMessageConverter`가 동작
    - 기본 문자처리 : `StringHttpMessageConverter`
    - 기본 객체처리 : `MappingJackson2HttpMessageConverter`
    - byte 처리 등등 기타 여러 `HttpMessageConverter`


- 객체가 들어오면 스프링은, 기본 디폴트: json방식으로 데이터를 만들어 HTTP응답에 반환
- HttpMessageConverter : 단순 문자면, StringConverter 동작. 객체면, Json Converter 동작.

- Jackson : json 객체를 json으로 바꿔주는 라이버러리.

<br>

## 3️⃣ 회원 관리 예제 - 백엔드 개발
### 01 비즈니스 요구사항 정리
- 데이터 : 회원 ID, 이름
- 기능 : 회원 등록, 조회
- 가상 시나리오 - 아직 데이터 저장소(DB) 선정 X (예: 성능 중요한 DB, 일반적인 관계형 DB, No SQL 등 정해지지 않은 상황)

<br>

<img src="./img/image10.png" width="400" height="170"/>

- 컨트롤러: 웹 MVC의 컨트롤러 역할
- 서비스: 핵심 비즈니스 로직 구현(예: 회원 중복가입 불가능 등). 비즈니스 도메인 객체를 가지고 핵심 비즈니스 로직이 동작하도록 구현한 객체.
- 리포지토리: 데이터베이스에 접근, 도메인 객체를 DB에 저장하고 관리
- 도메인: 비즈니스 도메인 객체, 예) 회원, 주문, 쿠폰 등 주로 데이터베이스에 저장하고 관리됨

<br>

<img src="./img/image11.png" width="350" height="250"/>

- 회원리포지토리, (회원 저장)은 인터페이스로 설계
- 구현체를 우선은 메모리 구현체로. (메모리 모드로 단순한 구현체) 구체적이 기술이 선정되면 바꿔끼움(바꾸기 위해 인터페이스 필요)

<br>

- 아직 데이터 저장소가 선정되지 않아서, 우선 인터페이스로 구현 클래스를 변경할 수 있도록 설계
- 데이터 저장소는 RDB, NoSQL 등등 다양한 저장소를 고민중인 상황으로 가정
- 개발을 진행하기 위해서 초기 개발 단계에서는 구현체로 가벼운 메모리 기반의 데이터 저장소 사용

<br>

### 02 회원 도메인과 리포지토리 만들기

- `optional` : null 일 때도 optional로 감싸서 반환
- `findAll` : 지금까지 저장된 회원 리스트 모두 반환
- `findAny` : 하나만 찾음. 끝까지 돌고 없으면 optional에 null 포함돼서 반환

### 03 회원 리포지토리 테스트 케이스 작성

## 4️⃣ 스프링 빈과 의존관계
## 5️⃣ 회원 관리 예제 - 웹 MVC 개발
## 6️⃣ 스프링 DB 접근 기술
## 7️⃣ AOP
## 8️⃣ 다음으로