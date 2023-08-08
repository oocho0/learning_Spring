package hello.hellospring.repository;

import hello.hellospring.domain.Member;
//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    /*
    @AfterEach = 매 테스트가 끝날때마다 실행될 코드
    테스트 코드 실행 순서는 정해지지 않음 또한, 테스트 코드 짤 때 실행 순서를 고려하면 절대 안됨
    그러므로 테스트 코드가 실행되어 서로 영향을 줄 수 있기 때문에 반드시 이 코드를 넣어서 테스트 코드가 실행된 후 마다
    이 코드가 자동으로 실행되어 테스트 코드가 저장되는 repository를 완전히 지워줘야함
     */
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        // "spring"으로 Member형의 변수 member의 이름을 setting
        member.setName("spring");
        // MemoryMemberRepositoy형의 변수 repository에 member를 저장
        repository.save(member);
        // Member형의 result 변수(결과저장)에 member의 아이디 값을 가져와서 repository에서 찾은 다음 그 값은 get()해서 가져옴
        Member result = repository.findById(member.getId()).get();
        /*
        이런 식으로 출력해서 결과값과 member가 같은지 다른지 확인할 수 있음
        System.out.println("result = "+ (result == member));
         */
        /*
        그러나 일일이 출력결과를 볼 수 없어서 Assertions 사용
        org.junit.jupiter.api의 Assertions
        assertEquals(같기를 기대되는 값, 같음을 비교할 결과 값);
        돌렸을 때 초록불이면 true 빨간색으로 에러가 나면 false
        Assertions.assertEquals(member, result);
         */
        /*
        요즘 많이 쓰는 것 : org.assertj.core.api의 Assertions
        assertThat(같음을 비교할 결과 값).isEqualTo(같기를 기대되는 값);
        import static으로 Assertions를 받으면 앞에 Assertions를 붙이지 않고도 assertThat을 부를 수 있음
         */
        assertThat(result).isEqualTo(member);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {

        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }

}
