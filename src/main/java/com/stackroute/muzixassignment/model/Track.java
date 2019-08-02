package com.stackroute.muzixassignment.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class Track {
    @Id
    private int id;

    @Column
    private String name;

    @Column
    private String Comments;

    public Track() {
    }

    public Track(int id, String name, String comments) {
        this.id = id;
        this.name = name;
        Comments = comments;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + name + '\'' +
                ", lastName='" + Comments + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        this.Comments = comments;
    }



}
