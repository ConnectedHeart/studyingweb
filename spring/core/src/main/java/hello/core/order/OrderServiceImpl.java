package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
<<<<<<< HEAD
=======
import lombok.RequiredArgsConstructor;
>>>>>>> c1c6fbaf10eca3b9c63efad69901427b1fa0c16a
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
<<<<<<< HEAD
=======
// final이 붙은 필드 값을 주입하는 생성자를 만들어줌.
@RequiredArgsConstructor
>>>>>>> c1c6fbaf10eca3b9c63efad69901427b1fa0c16a
public class OrderServiceImpl implements OrderService{

    //private final MemberRepository memberRepository = new MemoryMemberRepository();
    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    //private final DiscountPolicy discountPolicy = new RateDiscountPolicy(); // 인터페이스와 구현체에 모두 의존.
    //private DiscountPolicy discountPolicy; // 인터페이스에만 의존하도록 수정.
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

<<<<<<< HEAD
=======
    /* 롬복이 제공하는
    RequiredArgsContructor 기능덕분에 생성자를 만들지 않아도 됨.
>>>>>>> c1c6fbaf10eca3b9c63efad69901427b1fa0c16a
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
<<<<<<< HEAD
=======
    */
>>>>>>> c1c6fbaf10eca3b9c63efad69901427b1fa0c16a

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도 (싱글톤인지 확인)
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
