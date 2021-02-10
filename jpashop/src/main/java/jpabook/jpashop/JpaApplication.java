package jpabook.jpashop;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaApplication {

  public static void main(String[] args) {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpashop");

    EntityManager entityManager = entityManagerFactory.createEntityManager();

    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    try{
      // 비영속 상태

      transaction.commit();
    }catch (Exception e) {
      transaction.rollback();
    }finally {
      entityManager.close();
      entityManagerFactory.close();
    }

  }
}

