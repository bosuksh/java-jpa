package jpabasic.jpaexample;

import javax.persistence.*;

@Entity
@Table(name = "ORDERS")
public class Order {

  @Id @GeneratedValue
  private Long id;

  @ManyToOne
  @JoinColumn(name = "MEMBER_ID")
  private Member member;

  @ManyToOne
  @JoinColumn(name = "PRODUCT_ID")
  private Product product;

}
