package hello.core.lifecyle;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        // ConfigurableApplicationContext 인터페이스를 사용하거나, AnnotationConfigApplicationContext 를 사용해야 .close메소드를 쓸 수 있다.
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();

    }

    @Configuration
    static class LifeCycleConfig {
        // 2 빈 등록 초기화, 소멸 메소드 지정
        //@Bean(initMethod = "init", destroyMethod = "close")
        
        //3 Annotation사용 - NetworkClient에서 @PostContstruct와 @PreDestroy사용
        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}
