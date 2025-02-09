package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * @Controller 사용 또는
 * @Component @RequestMapping을 class레벨에 적어줌.
 * -> RequestMappingHanddlerMapping이 핸들러 대상으로 인식함.
 */
@Controller
public class SpringMemberFormControllerV1 {

    @RequestMapping("/springmvc/v1/members/new-form")
    public ModelAndView process() {
        return new ModelAndView("new-form");
    }


}
