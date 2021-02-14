package jpabasic.jpaexample;

import javax.persistence.*;

@Entity
@Table(name = "ORDERS")
public class Order {

  @Id @GeneratedValue
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MEMBER_ID")
  private Member member;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "PRODUCT_ID")
  private Product product;

}
