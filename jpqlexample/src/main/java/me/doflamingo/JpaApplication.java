package me.doflamingo;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaApplication {

  public static void main(String[] args) {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpql");

    EntityManager entityManager = entityManagerFactory.createEntityManager();

    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    try{


      transaction.commit();
    }catch (Exception e) {
      transaction.rollback();
    }finally {
      entityManager.close();
      entityManagerFactory.close();
    }

  }
}

