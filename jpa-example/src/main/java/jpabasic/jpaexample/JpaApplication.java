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

      Member member = new Member();
      member.setUsername("Member 1");
      member.setHomeAddress(new Address("city", "street", "10000"));
      member.setPeriod(new Period());
      member.getAddressHistory().add(new Address("city1","street1", "10000"));
      member.getFavoriteFoods().add("치킨");
      member.getFavoriteFoods().add("피자");
      member.getFavoriteFoods().add("족발");
      entityManager.persist(member);

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

