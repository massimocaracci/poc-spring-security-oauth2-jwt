package com.pantasoft.jwtauthserver.repositories.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RoleEntity {


    @Id
    @GeneratedValue
    private Long id;
    private String name;

    RoleEntity() {
    }

    public RoleEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
