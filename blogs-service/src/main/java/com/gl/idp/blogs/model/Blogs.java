package com.gl.idp.blogs.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "blogs")
public class Blogs {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "user_id")
    private Integer userId;

    @Basic
    @Column(name = "username")
    private String username;

    @OneToMany(mappedBy = "blogByBlogId", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Collection<Comments> commentsById;

    @OneToMany(mappedBy = "blogByBlogId", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Collection<LikesUnlikes> likesUnlikesById;

    @Basic
    @Column(name = "is_approved")
    private boolean isApproved = false;

}

