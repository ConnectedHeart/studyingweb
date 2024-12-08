package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
// ComponentScan : @Component Annotation 이 붙은 클래스를 찾아서 다 자동으로 스프링 bean에 등록해줌,
// AppConfig에도 @Configuration annotation이 붙어있고, @Configuration은 @Component 대상이기 때문에 자동으로 bean이 등록된다.
// AutoAppConfig와 AppConfig의 충돌 방지를 위해서 필터를 걸어둠. 또, 테스트 목적으로 만든 클래스에도 @Configuration이 존재함.
// 실무에서는 Configuration을 ComponentScan 대상에서 제외하지 않는다.
// Component Annotation이 붙은 클래스를 스프링 빈에 자동으로 등록해주는데, 기존 AppConfig와 다르게 AutoAppConfig에는
// 의존 주입 관계를 어떻게 해줘야할 지에 대한 정보가 없다.
// 의존 관계를 자동으로 주입해주는 Autowired Annotation을 사용한다. (생성자에 붙이면 됨.)
// ComponentScan을 쓰면 Autowired를 쓰게 된다.
// (스프링 컨테이너가 자동으로 빈을 찾아 주입하는데, 타입으로 대상을 조회한다. getBean(MemberRepository.class)와 같은 방식이라 보면 됨.)
// 스프링 빈의 기본 이름은 클래스명을 사용하되 맨 앞글자만 소문자로 사용한다.
// 빈 이름을 직접 지정하려면 @Component("memberService")와 같이 이름을 부여하면 된다.

//basepackages : 컴포넌트 스캔의 대상이 되는 패키지를 지정할 수 있음. 지정한 패키지의 하위 패키지를 모두 뒤짐.
// 라이브러리들을 포함한 모든 자바 클래스들이 스캔의 대상이 될 수 있기 때문에 패키지 지정을 해주는 것이 좋다.
// 패키지 지정을 하지 않으면, ComponentScan 어노테이션을 붙인 설정 정보 클래스의 패키지가 시작위치가 되고,
// 해당 패키지의 하위패키지들까지가 기본 스캔 대상이 된다.
// 패키지 위치를 지정하지않고, 설정 정보 클래스의 위치를 프로젝트의 최상단에 두는 방식으로 쓰는 것이 관례이다.
// 스프링 부트를 사용한다면, @SpringBootApplication를 프로젝트 시작 루트 위치에 두는 것이 관례이다.

// 컴포넌트 스캔 기본 대상 : @Component, @Controller, @Service, @Repository, @Configuration

@ComponentScan (
    basePackages = "hello.core.member",
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)

public class AutoAppConfig {

}
