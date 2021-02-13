package jpabasic.jpaexample;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class JpaApplication {

  public static void main(String[] args) {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");

    EntityManager entityManager = entityManagerFactory.createEntityManager();

    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    try{
      Member member = new Member();

      member.setUsername("doflamingo");
      entityManager.persist(member);
      entityManager.flush();
      entityManager.clear();

      Member referenceMember = entityManager.getReference(Member.class, member.getId());
      System.out.println("member = " + referenceMember.getClass());
      Member findMember = entityManager.find(Member.class, member.getId());
      System.out.println("isLoaded = " + entityManagerFactory.getPersistenceUnitUtil().isLoaded(Member.class));
      System.out.println("findMember.getUsername() = " + findMember.getUsername());
      System.out.println("referenceMember.getUsername() = " + referenceMember.getUsername());

      // instance of
      System.out.println("findMember == referenceMember = " + (referenceMember.getClass() == Member.class));


      transaction.commit();
    }catch (Exception e) {
      transaction.rollback();
      System.out.println(e.getMessage());
    }finally {
      entityManager.close();
      entityManagerFactory.close();
    }

  }
}

