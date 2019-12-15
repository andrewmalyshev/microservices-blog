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
    private int id;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "user_id")
    private int userId;

//    @ManyToOne
//    @JsonManagedReference
//    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
//    private User userByUserId;

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

