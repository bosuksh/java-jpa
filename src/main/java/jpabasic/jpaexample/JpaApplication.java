package jpabasic.jpaexample;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaApplication {

  public static void main(String[] args) {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");

    EntityManager entityManager = entityManagerFactory.createEntityManager();

    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    try{
      // 비영속 상태
      Member member = new Member();
      member.setId(1L);
      member.setName("Hello");
      //영속상태
      entityManager.persist(member);

      entityManager.flush();
      System.out.println("======================");

      transaction.commit();
    }catch (Exception e) {
      transaction.rollback();
    }finally {
      entityManager.close();
      entityManagerFactory.close();
    }

  }
}

