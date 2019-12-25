package com.gl.idp.blogs.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "likes_unlikes")
public class LikesUnlikes {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "type")
    private String type;

    @Basic
    @Column(name = "user_id", insertable = false, updatable = false)
    private Integer userId;

    @Basic
    @Column(name = "blog_id", insertable = false, updatable = false)
    private Integer blogId;

//    @ManyToOne
//    @JsonManagedReference
//    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
//    private User userByUserId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "blog_id", referencedColumnName = "id", nullable = false)
    private Blogs blogByBlogId;

}
