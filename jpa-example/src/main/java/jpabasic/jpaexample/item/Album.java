package jpabasic.jpaexample.item;

import javax.persistence.Entity;

@Entity
public class Album extends Item{
  private String artist;

  public String getArtist() {
    return artist;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }
}
