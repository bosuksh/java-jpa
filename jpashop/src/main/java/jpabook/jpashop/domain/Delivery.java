package jpabook.jpashop.domain;

import javax.persistence.*;

@Entity
public class Delivery {

  @Id @GeneratedValue
  @Column(name = "DELIVERY_ID")
  private Long id;

  private String city;
  private String zipcode;
  @Enumerated(EnumType.STRING)
  private DeliveryStatus status;
  @OneToOne(mappedBy = "delivery")
  private Order order;
}