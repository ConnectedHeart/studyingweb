package hello.core.Singleton;

public class SingletonService {
    // static 영역에 자기 자신을 하나 만들어놓고,
    private static final SingletonService instance = new SingletonService();

    // getInstance 메소드를 통해 생성된 객체에 접근한다.
    public static SingletonService getInstance() {
        return instance;
    }

    // 생성자를 private으로 선언하여, 외부에서 객체 생성을 막는다.
    private SingletonService() {

    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }

}
