package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    /**
     * 같은 저장소를 사용하고 있는 것이 아니기 때문에 정확한 테스트 방법이 아님.
     * 필드 주입이 아닌 생성자를 활용한 주입이 필요함.
     * 외부에서 memberRepository를 집어넣어줌. Dependency Injection(DI) 의존성 주입
     */

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

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
