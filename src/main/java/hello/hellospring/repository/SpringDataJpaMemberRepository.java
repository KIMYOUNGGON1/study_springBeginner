package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    /**
     * JPQL select m from Member m where m.name = ? 와 같음
     *  findByNameAndId 등으로 공통화가 가능한 비즈니스 로직이 아닌 비즈니스 로직 생성 가능.
     *  이처럼 단순한 비즈니스 로직의 경우 interface를 통해 해결이 가능함.
     *
     *  복잡한 동적 쿼리는 Querydsl 이라는 라이브러리를 사용하여 해결 가능함.
     */

    @Override
    Optional<Member> findByName(String name);
}
