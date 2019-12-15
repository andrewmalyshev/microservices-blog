package com.gl.idp.blogs.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    int id;
    int userId;
    int blogId;
    String description;
}
