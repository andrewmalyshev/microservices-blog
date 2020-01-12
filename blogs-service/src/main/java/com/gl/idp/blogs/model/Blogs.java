package com.gl.idp.blogs.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Document("blogs")
public class Blogs {

    @Id
    private String id;

    private String description;

    private Integer userId;

    private String username;

    private List<Comments> comments = new ArrayList<>();

    private List<LikesUnlikes> likesUnlikes= new ArrayList<>();

    private boolean approved = false;

}

