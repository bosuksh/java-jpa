package jpabasic.jpaexample;

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
      // 비영속 상태

      //팀저장
      Team teamA = new Team();
      teamA.setName("TEAM A");
      entityManager.persist(teamA);

      Team teamB = new Team();
      teamB.setName("TEAM B");
      entityManager.persist(teamB);


      //멤버저장
      Member member = new Member();
      member.setUsername("USER1");
      member.updateTeam(teamA);
      entityManager.persist(member);
      entityManager.flush();
      entityManager.clear();

      Member findMember = entityManager.find(Member.class, 3L);
      findMember.updateTeam(teamB);

      Team team = findMember.getTeam();
      List<Member> memberList = team.getMemberList();
      for (Member m : memberList) {
        System.out.println("m = " + m.getUsername());
      }

      transaction.commit();
    }catch (Exception e) {
      transaction.rollback();
    }finally {
      entityManager.close();
      entityManagerFactory.close();
    }

  }
}

