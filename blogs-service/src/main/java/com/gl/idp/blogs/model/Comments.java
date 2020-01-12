package com.gl.idp.blogs.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
public class Comments {

    private String description;


    private Integer userId;


    private String blogId;


    private String username;

}
