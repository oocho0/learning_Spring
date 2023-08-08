package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {
    /* 이렇게 새로 생성해버리면 static이라 하더라도 이전에 생성해놓은 memberRepository와 별개의 repository인스턴스가 생성되는 것이므로 사용하지 않음
    private final MemberRepository memberRepository = new MemberRepository();음
     */
    private final MemberRepository memberRepository;
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     */
    public long join(Member member) {

        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 같은 이름의 중복 회원 방지 메소드
     */
    private void validateDuplicateMember(Member member) {
        /* 아래같이 Optional을 바로 꺼내는 건 권장하지 않음
        Optional<Member> result = memberRepository.findByName(member.getName());
        result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
        .ifPresent(있는 경우 실행)
        그래서 아래와 같이 한줄로 다 써줌 */
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /**
     * 회원 아이디로 조회
     */
    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
