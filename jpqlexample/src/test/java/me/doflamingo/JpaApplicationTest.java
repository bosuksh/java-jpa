package me.doflamingo;

import me.doflamingo.domain.Member;
import me.doflamingo.domain.Team;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.List;

import static me.doflamingo.domain.MemberType.ADMIN;

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
 public void jpqlType() {
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
 public void jpqlCase() {
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
 
 @Test
 @DisplayName("경로표현식")
 public void implicitInnerJoin() {
   //given
   Member member = new Member();
   member.setUsername("member1");
   Member member1 = new Member();
   member1.setUsername("member2");
   Team team = new Team();
   team.setName("team1");
   team.addMember(member);
   team.addMember(member1);
   entityManager.persist(team);
   //when
   String query = "select m.username from Team t join t.memberList m";
   List<String> resultList = entityManager.createQuery(query, String.class).getResultList();
   System.out.println("resultList = " + resultList);
   //then
   transaction.commit();
 }
 @Test
 @DisplayName("fetch join")
 public void fetchJoin() {
   //given
   Member member1 = new Member();
   member1.setUsername("회원1");
   Member member2 = new Member();
   member2.setUsername("회원2");
   Member member3 = new Member();
   member3.setUsername("회원3");
   Member member4 = new Member();
   member4.setUsername("회원4");

   Team team1 = new Team();
   team1.setName("팀A");
   team1.addMember(member1);
   team1.addMember(member2);
   Team team2 = new Team();
   team2.setName("팀B");
   team2.addMember(member3);
   entityManager.persist(team1);
   entityManager.persist(team2);

   entityManager.flush();
   entityManager.clear();

   /**
    * Member을 먼저 가져오고 LazyLoading에 의해서 Team을 프록시 객체로 만듦
    * 총 SQL문 3개가 나간다. N+1문제 발생
    */
   String query = "select m from Member m";
   List<Member> resultList = entityManager.createQuery(query, Member.class).getResultList();
   System.out.println("resultList.size() = " + resultList.size());
   for (Member member: resultList) {
     //여기서 select문이 하나씩 나감
     System.out.println("member = " + member.getUsername()+" team = "+member.getTeam().getName());
   }
   entityManager.flush();
   entityManager.clear();

    /**
    * fetch join으로 한번의 쿼리만 발생
    */
   query = "select m from Member m join fetch m.team";
   resultList = entityManager.createQuery(query, Member.class).getResultList();
   System.out.println("resultList.size() = " + resultList.size());
   for (Member member: resultList) {
     System.out.println("member = " + member.getUsername()+" team = "+member.getTeam().getName());
   }
 }

 @Test
 @DisplayName("fetch join collection")
 public void fetchJoinCollection() {
   //given
   Member member1 = new Member();
   member1.setUsername("회원1");
   Member member2 = new Member();
   member2.setUsername("회원2");
   Member member3 = new Member();
   member3.setUsername("회원3");
   Member member4 = new Member();
   member4.setUsername("회원4");

   Team team1 = new Team();
   team1.setName("팀A");
   team1.addMember(member1);
   team1.addMember(member2);
   Team team2 = new Team();
   team2.setName("팀B");
   team2.addMember(member3);
   entityManager.persist(team1);
   entityManager.persist(team2);

   entityManager.flush();
   entityManager.clear();
   /**
    *  fetch join을 컬렉션에 수행할 시 (일대다, 다대다) 값이 증가할 수 있는데
    *  그 이유는 table을 join하면서 하나의 팀에 대한 member가 member수 만큼 row가 생성되기 때문이다.
    *  그러나 jpa입장에서는 이게 중복인지 확인할 수 없기 때문에 row개수대로 출력하게 되는 것이다.
    */
   String query = "select t from Team t join fetch t.memberList";
   List<Team> resultList = entityManager.createQuery(query, Team.class).getResultList();
   System.out.println("resultList.size() = " + resultList.size());
   for (Team team : resultList) {
     System.out.println("team = " + team.getName());
     for(Member member: team.getMemberList())
       System.out.println("-> member = " + member);
   }

   /**
    * 이 방법을 해결하기 위해서 distinct를 사용하면 된다.
    * JPQL의 distinct는 SQL에서도 distinct 시켜주고
    * 영속성 컨텍스트에서도 같은 id를 가진 값에 대해서 distinct를 해준다.
    */

   query = "select distinct t from Team t join fetch t.memberList";
   resultList = entityManager.createQuery(query,Team.class).getResultList();
   System.out.println("resultList.size() = " + resultList.size());
   for (Team team : resultList) {
     System.out.println("team = " + team.getName());
     for(Member member: team.getMemberList())
       System.out.println("-> member = " + member);
   }
 }

 @Test
 @DisplayName("fetch join paging api")
 public void fetchJoinPaging() throws Exception {
   //given
   Member member1 = new Member();
   member1.setUsername("회원1");
   Member member2 = new Member();
   member2.setUsername("회원2");
   Member member3 = new Member();
   member3.setUsername("회원3");
   Member member4 = new Member();
   member4.setUsername("회원4");

   Team team1 = new Team();
   team1.setName("팀A");
   team1.addMember(member1);
   team1.addMember(member2);
   Team team2 = new Team();
   team2.setName("팀B");
   team2.addMember(member3);
   entityManager.persist(team1);
   entityManager.persist(team2);

   entityManager.flush();
   entityManager.clear();
   /**
    * fetch join은 컬렉션에서 페이징을 사용할수 없다.
    * 그래서 대안으로 사용하는 것이 @BatchSize로
    * LazyLoading시 가져올 사이즈를 정할 수 있다.
    * 예를 들어 size를 100으로 정해놓으면 LazyLoading으로 가져올 때
    * 100개를 한꺼번에 가져오는 것이다.
    * 이걸로 N+1문제를 해결하는데 도움이 된다.
    */
   String query = "select t from Team t";
   List<Team> resultList = entityManager.createQuery(query, Team.class)
                             .setFirstResult(0)
                             .setMaxResults(2)
                             .getResultList();
   //then
   System.out.println("resultList.size() = " + resultList.size());
   for (Team team : resultList) {
     System.out.println("team = " + team.getName());
     for(Member member: team.getMemberList())
       System.out.println("-> member = " + member);
   }
 }

 @Test
 @DisplayName("엔티티 직접 사용")
 public void useEntityDirectly() throws Exception {
   //given
   Member member1 = new Member();
   member1.setUsername("회원1");
   Member member2 = new Member();
   member2.setUsername("회원2");
   Member member3 = new Member();
   member3.setUsername("회원3");
   Member member4 = new Member();
   member4.setUsername("회원4");

   Team team1 = new Team();
   team1.setName("팀A");
   team1.addMember(member1);
   team1.addMember(member2);
   Team team2 = new Team();
   team2.setName("팀B");
   team2.addMember(member3);
   entityManager.persist(team1);
   entityManager.persist(team2);

   entityManager.flush();
   entityManager.clear();

   /**
    * DB는 entity를 식별자로 판단하기 떄문에
    * JPQL에서는 entity를 식별자 대신 사용해도 좋다.
    */
   //when
   String query = "select m from Member m where m = :member";
   Member findMember = entityManager.createQuery(query, Member.class)
                   .setParameter("member", member1)
                   .getSingleResult();

   System.out.println("findMember = " + findMember);

   query = "select m from Member m where m.id = :memberId";
   findMember = entityManager.createQuery(query, Member.class)
                         .setParameter("memberId", member1.getId())
                         .getSingleResult();

   System.out.println("findMember = " + findMember);

   //then
 }
}