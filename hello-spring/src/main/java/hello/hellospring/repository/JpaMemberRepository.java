package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        /* 영구 저장
         * JPA가 INSERT 쿼리 다 만들어서 DB에 집어 넣고
         * setId까지 다 해줌
         */
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        /* find(조회할 타입, 식별자 pk 값)
         * Optional 값이 없을 수도 있으므로 .ofNullable(member)로
         */
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        /* pk기반이 아닌 것은 JPQL을 작성해 주어야 함 */
        List<Member> result = em.createQuery("SELECT m FROM Member m WHERE m.name = :name", Member.class).setParameter("name", name).getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        /* JPQL 객체지향쿼리 : 객체(Entity)를 대상으로 쿼리를 날림 (보통은 테이블 대상으로 쿼리를 날림) -> SQL로 번역이 됨
         * Member Entity를 조회하라는 뜻 객체 자체를 SELECT함
         * 이미 Member Entity 안에 매핑이 다 되어있음
         */
        return em.createQuery("SELECT m FROM Member m", Member.class).getResultList();
    }
}
