package com.gl.idp.users.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @NotBlank(message = "First Name is required")
    @Basic
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    @Basic
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email! Please enter valid email")
    @Basic
    @Column(name = "email")
    private String email;

    @Basic
    @Column(name = "role_id", insertable = false, updatable = false)
    private int roleId;

    @NotBlank(message = "Password is required")
    @Basic
    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Basic
    @Column(name = "token")
    @JsonIgnore
    private String token;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role roleByRoleId;

    @Basic
    @Column(name = "is_enabled")
    private boolean isEnabled = false;

//    @OneToMany(mappedBy = "userByUserId")
//    @JsonBackReference
//    private Collection<Blogs> blogsById;
//
//    @OneToMany(mappedBy = "userByUserId")
//    @JsonBackReference
//    private Collection<LikesUnlikes> likesUnlikesById;

}
