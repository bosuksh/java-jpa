package jpabasic.jpaexample;

import jpabasic.jpaexample.item.Book;
import jpabasic.jpaexample.item.Item;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaApplication {

  public static void main(String[] args) {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");

    EntityManager entityManager = entityManagerFactory.createEntityManager();

    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    try{

      Book book = new Book();
      book.setName("JPA Book");
      book.setPrice(10000);
      book.setIsbn("12bs");
      book.setAuthor("aa");

      entityManager.persist(book);
      transaction.commit();
    }catch (Exception e) {
      transaction.rollback();
    }finally {
      entityManager.close();
      entityManagerFactory.close();
    }

  }
}

