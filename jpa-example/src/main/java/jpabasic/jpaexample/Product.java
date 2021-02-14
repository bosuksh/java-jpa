package jpabasic.jpaexample;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

  @Id @GeneratedValue
  private Long id;

  private String name;

  @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
  private List<Order> orderList = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
