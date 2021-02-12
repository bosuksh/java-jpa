package jpabook.jpashop.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {
  @Id @GeneratedValue
  @Column(name = "CATEGORY_ID")
  private Long id;
  private String name;

  @ManyToOne
  @JoinColumn(name = "PARENT_ID")
  private Category parent;

  @OneToMany(mappedBy = "parent")
  private List<Category> children;

  @ManyToMany
  @JoinTable(name = "CATEGORY_ITEM",
              joinColumns = @JoinColumn(name = "CATEGORY_ID"),
              inverseJoinColumns = @JoinColumn(name = "ITEM_ID")
  )
  private List<Item> items = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Item> getItems() {
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }
}