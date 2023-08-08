package hello.hellospring;

import hello.hellospring.aop.TimeTraceAop;
import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {

//    /* 아래 처럼 해도 됨
//    @Autowired
//    DataSource dataSource;
//     */
//
//    private DataSource dataSource;
//
//    /* @Configuration한 것도 스프링빈으로 관리가 되기 때문에
//     * Springboot가 application.properties안의 내용도 보고
//     * 자체적으로 데이터베이스와 연결할 정보를 가지고 있는 dataSource 스프링 빈을 생성
//     * 그리고 그걸 @Autowired 해서 dataSource에 DI 주입 해줌
//     */
//
//    @Autowired
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//
//    EntityManager em;
//
//    @Autowired
//    public SpringConfig(EntityManager em) {
//        this.em = em;
//    }
    /* 생성자를 생성해서 스프링 데이터 JPA가 구현체를 생성해서
     * 스프링 빈에 등록해놓은 memberRepository를 DI주입 받음 */
    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
//    @Bean
//    public MemberService memberService(){
//        return new MemberService(memberRepository());
//    }

    /* memberService에 의존관계를 셋팅 해줌 */
    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository);
    }

    /* 나중에 MemoryMemberRepository가 아닌 DBMemberRepository를
    * 사용하는 것으로 바뀌었을 때를 고려해서 MemoryMemberRepository타입이 아닌
    * 그 인터페이스타입인 MemberRepository타입으로 생성하는 것!
    * 대신 바뀌게된 DBMemberRepository는 반드시 MemberRepository의 구현체여야 함
    */
//    @Bean
//    public MemberRepository memberRepository(){
////        return new MemoryMemberRepository();
////        return new JdbcMemberRepository(dataSource);
////        return new JdbcTemplateMemberRepository(dataSource);
////        return new JpaMemberRepository(em);
//    }
}
