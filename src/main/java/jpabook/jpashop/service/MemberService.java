package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // JPA가 조회하는 곳에서 성능 최적화 해줌, 쓰기에는 넣으면 안됨
@RequiredArgsConstructor // final이 있는 필드만 가지고 생성자 만들어줌
public class MemberService {

    private final MemberRepository memberRepository;

    /*@Autowired // 생성자가 하나만 있는 경우 생략 가능
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {

        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     *회원 전체 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findALl();
    }

    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }

}
