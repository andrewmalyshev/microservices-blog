package com.gl.idp.blogs.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {
    Integer id;
    Integer userId;
    Integer blogId;
    String description;
}
