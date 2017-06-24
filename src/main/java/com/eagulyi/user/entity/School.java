
package com.eagulyi.user.entity;

import javax.persistence.*;

@Entity
@Table(name = "SCHOOL")
public class School {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name")
    private String name;

    public School() {
    }

    public School(String schoolName) {
        this.name = schoolName;
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
