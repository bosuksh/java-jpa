package jpabasic.jpaexample;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Parent {
  @Id @GeneratedValue
  private Long id;
  private String name;

  @OneToMany(mappedBy = "parent", orphanRemoval = false)
  private List<Child> children = new ArrayList<>();

  public void addChild(Child child) {
   children.add(child);
   child.setParent(this);
  }
  public List<Child> getChildren() {
    return children;
  }

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
}
