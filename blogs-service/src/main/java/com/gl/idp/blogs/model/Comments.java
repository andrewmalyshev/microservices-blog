package com.gl.idp.blogs.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "comments")
public class Comments {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "user_id", insertable = false, updatable = false)
    private int userId;

    @Basic
    @Column(name = "blog_id", insertable = false, updatable = false)
    private int blogId;

//    @ManyToOne
//    @JsonManagedReference
//    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
//    private User userByUserId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "blog_id", referencedColumnName = "id", nullable = false)
    private Blogs blogByBlogId;

}
