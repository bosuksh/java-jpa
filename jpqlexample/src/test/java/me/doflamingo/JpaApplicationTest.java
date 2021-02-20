package me.doflamingo;

import me.doflamingo.domain.Member;
import me.doflamingo.domain.MemberType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

import static me.doflamingo.domain.MemberType.*;

class JpaApplicationTest {

 private static EntityManagerFactory entityManagerFactory;
 private static EntityManager entityManager;
 private static EntityTransaction transaction;
 @BeforeAll
 static void setUp() {
  entityManagerFactory = Persistence.createEntityManagerFactory("jpql");

  entityManager = entityManagerFactory.createEntityManager();

  transaction = entityManager.getTransaction();
  transaction.begin();
 }

 @AfterAll
 static void postProcess() {
  entityManager.close();
  entityManagerFactory.close();
 }
 @Test
 @DisplayName("JPQL 타입표현")
 public void jpqlType() throws Exception {
   //given
   Member member = new Member();
   member.setUsername("member1");
   member.setMemberType(ADMIN);
   entityManager.persist(member);
   //when
  String query = "select m.username, 'HELLO', true from Member m " +
                   "where m.memberType = :memberType";
  List<Object[]> resultList = entityManager.createQuery(query, Object[].class)
                      .setParameter("memberType", ADMIN)
                      .getResultList();
  for (Object[] objects : resultList) {
   System.out.println("objects[0] = " + objects[0]);
   System.out.println("objects[1] = " + objects[1]);
   System.out.println("objects[2] = " + objects[2]);
  }
  //then
 }

 @Test
 @DisplayName("JPQL 조건식")
 public void jpqlCase() throws Exception {
   //given
   for(int i = 10; i< 100; i+=10){
    Member member = new Member();
    member.setAge(i);
    entityManager.persist(member);
   }
   //when
  String query = "select " +
                   "case " +
                    "when m.age <= 10 then '학생요금' " +
                    "when m.age >= 60 then '경로요금' " +
                    "else '일반요금' " +
                   "end " +
                 "from Member m";
  List<String> resultList = entityManager.createQuery(query, String.class).getResultList();
  //then
  for (String s : resultList) {
   System.out.println("s = " + s);
  }
  transaction.commit();
 }

}