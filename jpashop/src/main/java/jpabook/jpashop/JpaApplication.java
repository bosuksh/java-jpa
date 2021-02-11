package jpabook.jpashop;

import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;

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
      Member member = new Member();
      Order order = new Order();
      entityManager.persist(member);
      entityManager.persist(order);
      member.addOrder(order);
      OrderItem orderItem = new OrderItem();
      entityManager.persist(orderItem);
      order.addOrderItems(orderItem);
      Item item = new Item();
      entityManager.persist(item);
      orderItem.updateItem(item);


      transaction.commit();
    }catch (Exception e) {
      transaction.rollback();
    }finally {
      entityManager.close();
      entityManagerFactory.close();
    }

  }
}

