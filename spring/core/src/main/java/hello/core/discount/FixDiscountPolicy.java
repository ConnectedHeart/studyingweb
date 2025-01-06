package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
<<<<<<< HEAD

=======
import org.springframework.stereotype.Component;

@Component
>>>>>>> c1c6fbaf10eca3b9c63efad69901427b1fa0c16a
public class FixDiscountPolicy implements DiscountPolicy{

    private int discountFixAmount = 1000; // 1000원 할인

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return discountFixAmount;
<<<<<<< HEAD
        }
        return 0;
=======
        } else {
            return 0;
        }
>>>>>>> c1c6fbaf10eca3b9c63efad69901427b1fa0c16a
    }
}
