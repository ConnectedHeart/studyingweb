package hello.core.Singleton;

public class StatefulService {
    /* 스프링 싱글톤 사용 시 상태를 공유하는 필드가 존재하고, 이를 변경하는 부분이 존재한다면
    동시성 문제가 발생할 수 있음.
    private int price; // 상태를 유지하는 필드

    public void order(String name, int price) {
        System.out.println("name = " + name + "price = " + price);
        this.price = price; //여기가 문제!
    }
    */
    public int order(String name, int price) {
        System.out.println("name = " + name + "price = " + price);
        return price;
    }

    /*
    public getPrice() {
        return price;
    }
    */
}
