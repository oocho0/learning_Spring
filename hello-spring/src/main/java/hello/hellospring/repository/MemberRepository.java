package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);

    /**
     * 아이디 찾기
     * null 값일 때 null로 바로 반환하지 않고 Optional로 감싸서 반환하게 함
     * @param id
     * @return
     */
    Optional<Member> findById(Long id);

    /**
     * 이름 찾기
     * null 값일 때 null로 바로 반환하지 않고 Optional로 감싸서 반환하게 함
     * @param name
     * @return
     */
    Optional<Member> findByName(String name);

    /**
     * 저장된 모든 회원 정보를 리스트로 찾기
     * @return
     */
    List<Member> findAll();
}
