package jpabasic.jpaexample;

import jpabasic.jpaexample.Address;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ADDRESS")
public class AddressEntity {
  @Id @GeneratedValue
  private Long id;

  private Address address;

  public AddressEntity() {
  }

  public AddressEntity(Address address) {
    this.address = address;
  }

  public Long getId() {
    return id;
  }

  public Address getAddress() {
    return address;
  }
}
