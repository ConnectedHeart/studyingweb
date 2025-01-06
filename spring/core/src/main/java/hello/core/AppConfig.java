package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    //@Bean memberService 호출 -> new MemoryMemberRepository() 호출
    //@Bean orderService 호출 -> new MemoryMemberRepository() 호출, RateDiscountPolicy() 호출
    // : 궁금증 - AppConfig의 factoryMethod를 호출하면서 파라미터 객체들을 생성하는 생성자가 호출되는데 이러면 싱글톤 패턴이 깨지는 게 아닐까?
    // 스프링 컨테이너는 이 문제를 어떻게 해결해줄까?

    // bean 생성 시 예상 결과
    // memberService 에서 call AppConfig.memberService, call AppConfig.memberRepository 출력
    // memberRepository 에서 call AppConfig.memberRepository 출력
    // orderService 에서 call AppConfig.orderService, call AppConfig.orderService 출력

    // 실제 결과
    // call AppConfig.memberService
    // call AppConfig.memberRepository
    // call AppConfig.orderService
<<<<<<< HEAD
=======
    /*
>>>>>>> c1c6fbaf10eca3b9c63efad69901427b1fa0c16a
    @Bean
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemoryMemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        //return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
<<<<<<< HEAD

=======
       */
>>>>>>> c1c6fbaf10eca3b9c63efad69901427b1fa0c16a
}
