package com.gl.idp.blogs.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
public class LikesUnlikes {


    private String type;

    private Integer userId;

    private String blogId;

//    @ManyToOne
//    @JsonManagedReference
//    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
//    private User userByUserId;

}
