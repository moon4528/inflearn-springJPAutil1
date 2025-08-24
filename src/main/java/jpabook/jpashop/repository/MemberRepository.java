package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    //@PersistenceContext
    //private EntityManager em;

    public void save(Member member) {
        em.persist(member); // 영속성 컨텍스트에 들어가는 시점에 pk 값을 채워줌 (DB에 들어가기 전 시점)
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findALl() {
        return em.createQuery("select m from Member m", Member.class) // JPQL : 엔티티 객체를 대상으로 쿼리
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
