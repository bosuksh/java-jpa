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

      Parent parent = new Parent();
      Child child1 = new Child();
      Child child2 = new Child();

      parent.addChild(child1);
      parent.addChild(child2);

      entityManager.persist(parent);

      entityManager.remove(parent);


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

