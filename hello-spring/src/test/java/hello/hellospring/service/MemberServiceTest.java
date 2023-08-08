package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

    /* 이렇게 새로 생성해버리면 static이라 하더라도 이전에 생성해놓은 memberRepository와 별개의 repository인스턴스가 생성되는 것이므로 사용하지 않음
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
     */
    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach(){
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }


    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }


    @Test
    void join() {
        //given - 주어진 상황
        Member member = new Member();
        member.setName("hello");

        //when - 실행 했을 때
        Long saveId = memberService.join(member);

        //then - 이게 나와야 함(검증부)

        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        /* try-catch문으로 해결하는 경우
        try{
            memberService.join(member2);
            // 만약 try문에서 위에 코드가 catch되지 않고 넘어왔다면
            // 검증 실패기 때문에 fail() 메소드를 실행시켜서 검증에 실패했음을 알려줌
            fail("예외가 발생해야 합니다.");
        }catch (IllegalStateException e){
            // catch문으로 Exception이 잡혀서 들어왔다면 join에서 발생시킨 예외의 메세지가 맞는지 한 번 더 체크
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }*/
        //then

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}