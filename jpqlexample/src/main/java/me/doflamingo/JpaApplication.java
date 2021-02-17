package me.doflamingo;


import me.doflamingo.domain.Member;
import me.doflamingo.domain.MemberDto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaApplication {

  public static void main(String[] args) {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpql");

    EntityManager entityManager = entityManagerFactory.createEntityManager();

    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    try{
      Member member = new Member();
      member.setUsername("member1");
      member.setAge(10);
      entityManager.persist(member);

      Member findMember = entityManager.createQuery("select m from Member m where m.username = :username", Member.class)
                              .setParameter("username", "member1")
                              .getSingleResult();

      System.out.println("findMember = " + findMember.getId());

      // QueryType으로 조회
      List resultList = entityManager.createQuery("select distinct m.username, m.age from Member m")
                          .getResultList();

      Object[] objects = (Object[])resultList.get(0);
      System.out.println("username = " + objects[0]);
      System.out.println("age = " + objects[1]);

      // Object[] 타입으로 조회
      List<Object[]> resultList2 = entityManager.createQuery("select distinct m.username, m.age from Member m", Object[].class)
        .getResultList();
      Object[] objects2 = resultList2.get(0);
      System.out.println("username = " + objects2[0]);
      System.out.println("age = " + objects2[1]);

      //MemberDto로 조회
      List<MemberDto> resultList3 = entityManager.createQuery("select distinct new me.doflamingo.domain.MemberDto(m.username, m.age) from Member m", MemberDto.class)
                                      .getResultList();
      MemberDto memberDto = resultList3.get(0);
      System.out.println("username = " + memberDto.getUsername());
      System.out.println("age = " + memberDto.getAge());

      transaction.commit();
    }catch (Exception e) {
      transaction.rollback();
    }finally {
      entityManager.close();
      entityManagerFactory.close();
    }

  }
}

