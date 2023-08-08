package hello.hellospring.domain;

import javax.persistence.*;

@Entity
public class Member {
    /**
     * 비즈니스 요구 사항 : 회원 데이터 (회원ID)
     * 회원이 정하는 값이 아닌 시스템이 임의로 부여하는 아이디 값
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * 비즈니스 요구 사항 : 회원 데이터 (이름)
     */
    @Column(name = "name")
    private String name;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
