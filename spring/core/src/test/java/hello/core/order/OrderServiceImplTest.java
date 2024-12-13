package hello.core.order;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {
    // OrderServiceImpl 에 생성자 주입 대신 수정자 주입을 사용한다면,
    // 아래 코드에서 에러가 발생함. memberRepository와 discountPolicy 를 세팅해주는 곳이 없음.
    // 생성자로 의존 주입을 사용하게 되면 주입하는 부분을 생략할 수 없음.
    // 수정자로 의존 주입을 사용하게 되면 주입하는 부분을 생략할 가능성이 존재함.
    /*
    @Test
    void createOrder() {
        OrderServiceImpl orderService = new OrderServiceImpl();
        orderService.createOrder(1L,"itemA", 10000);
    }
    */

}