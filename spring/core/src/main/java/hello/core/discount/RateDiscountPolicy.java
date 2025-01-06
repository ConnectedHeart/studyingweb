package hello.core.discount;

<<<<<<< HEAD
=======
import hello.core.annotation.MainDiscountPolicy;
>>>>>>> c1c6fbaf10eca3b9c63efad69901427b1fa0c16a
import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.stereotype.Component;

<<<<<<< HEAD
@Component
=======
//@Component
@MainDiscountPolicy
>>>>>>> c1c6fbaf10eca3b9c63efad69901427b1fa0c16a
public class RateDiscountPolicy implements DiscountPolicy{

    private int discoutPercent = 10;
    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return price * discoutPercent / 100;
        } else {
            return 0;
        }
    }
}
