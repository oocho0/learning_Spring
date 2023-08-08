package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {
    /**
     * 담을 공간 Map<key, value>
     */
    private static Map<Long,Member> store = new HashMap<>();
    /**
     * 0,1,2... 키 값을 넣어줌
     */
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        //키값을 1씩 증가하며 아이디 값을 넣어줌
        member.setId(++sequence);
        //Map store 에 key-아이디:value-맴버객체로 넣어줌
        // (아이디 값은 이미 회원이 회원가입할때 기입해 넣어서 들어있는 값)
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // null 값이 있으면 .ofNullable을 통해 optional로 감싸서 반환
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        // member에서 name과 같으면 true-> filter되어서 Optional로 반환
        return store.values().stream().filter(member -> member.getName().equals(name)).findAny();
    }

    @Override
    public List<Member> findAll() {
        // store의 value인 member들을 ArrayList로 반환
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}
