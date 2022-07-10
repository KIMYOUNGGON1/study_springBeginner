package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    /**
     * test method의 명칭은 외국인과 함께 일하는 것이 아니라면 한글로 직관적이게 작성하는 것을 권장한다.
     *
     * Spring을 통한 test case 실행은 Spring Frame Work를 붙이고 테스트 코드를 작성하기 때문에
     * 실행하는데 오랜 시간이 걸릴 수 밖에 없음.
     * 차라리 간단한 자바 코드로 돌아가는 단위 테스트 케이스를 작성하는 게 좋음.(그게 무조건 좋다기 보다는 그런 테스트 케이스가 좋을 확률이 높다.)
     *
     * 협업에서 100 중에 70~80 정도가 테스트 코드를 작성하는데 할애됨.
     * 서비스의 크기가 커질수록 조그만한 에러가 엄청난 손해로 직결되기 떄문에
     * 단위 테스트 코드를 잘 짜면서 작업하는 게 중요하다.
     *
     */

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
        //alt + enter 로 Assertions(assertj) statice import
    }

    @Test
    void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");
        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        /*
        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
       */

        // then
    }

}