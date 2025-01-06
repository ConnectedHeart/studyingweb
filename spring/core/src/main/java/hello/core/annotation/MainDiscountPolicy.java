package hello.core.annotation;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Qualifier("mainDiscountPolicy")
// 이런 식으로 annotaion으로 만들어두면 quailfier에서
// 이름을 잘못적는 오류가 발생했을 때엔 컴파일 오류가 발생하지 않지만,
// annotation을 사용하면 컴파일 오류가 발생해서 쉽게 오류를 찾을 수 있다.
public @interface MainDiscountPolicy {
}
