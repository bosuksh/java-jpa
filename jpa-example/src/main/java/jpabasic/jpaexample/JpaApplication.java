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

      Team team = new Team();
      team.setName("TeamA");

      Member member = new Member();
      member.setUsername("doflamingo");
      member.updateTeam(team);
      entityManager.persist(team);
      entityManager.persist(member);
      entityManager.flush();
      entityManager.clear();

      Member member1 = entityManager.find(Member.class, member.getId());
      System.out.println("----------------------");
      System.out.println("member1.getTeam().getClass() = " + member1.getTeam().getClass());
      System.out.println("----------------------");
      System.out.println(member1.getTeam().getName());


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

