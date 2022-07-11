package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {

    /**
     * 같은 저장소를 사용하고 있는 것이 아니기 때문에 정확한 테스트 방법이 아님.
     * 필드 주입이 아닌 생성자를 활용한 주입이 필요함.
     * 외부에서 memberRepository를 집어넣어줌. Dependency Injection(DI) 의존성 주입
     */

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 예를 들어서 실무에서 모든 method의 동작 시간을 측정해야 하는 상황이 발생했다면?
     *
     * 이것은 핵심 관심 사항도 아니며, 공통적으로 측정해야 하기에 공통 관심 사항이다.
     * 측정도 어렵지만 유지보수도 어려워진다.
     *
     * 이를 AOP로 해결이 가능해진다.
     * Aspect Oriented Programming
     * 공통 관심 사항(cross-cutting concern)과 핵심 관심 사항(core concern)을 분리함.
     *
     * 그리고 원하는 곳에 공통 관심 사항을 적용하는 것이다.
     */


    /**
     *회원가입
     */
    public Long join(Member member) {

        //같은 이름이 있는 중복 회원X
        validateDuplicateMember(member); //중복 회원 검증 (ctrl + alt + m으로 코드 리팩토링) => method화.
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * service의 경우 repository에 비해 method의 명칭이 비즈니스에 가깝다.
     * 그래야지 기획자와 개발자간의 소통이 원할하게 이뤄질 수 있다.
     */

    /**
     *  전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
