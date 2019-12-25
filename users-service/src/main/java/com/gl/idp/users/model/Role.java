package com.gl.idp.users.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Collection;

@Data
@EqualsAndHashCode(exclude = "usersById")
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "roleByRoleId")
    @JsonBackReference
    private Collection<User> usersById;

}
