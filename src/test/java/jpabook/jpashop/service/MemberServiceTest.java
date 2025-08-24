package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    // 기본적으로 Insert는 안됨
    //  (이유: DB transaction이 commit을 하는 순간 플러시 -> JPA 영속성 컨텍스트에 있는 객체가 Insert
    //  스프링에서의 transactional은 기본적으로 commit을 안하고 rollback함)
    //  @Rollback(false) 하면 DB에 Insert문 볼 수 있음
    @Test
    //@Rollback(false)
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("moon");

        //when
        Long savedId = memberService.join(member);

        //then
        em.flush(); //flush : 영속성 컨텍스트에 있는 변경이나 등록 내용을 DB에 반영함 (@Rollback(false)안하고 Insert 볼 수 있음)
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("moon");

        Member member2 = new Member();
        member2.setName("moon");

        //when
        memberService.join(member1);

        //then
        assertThrows(IllegalStateException.class, () ->
                memberService.join(member2));
    }
}