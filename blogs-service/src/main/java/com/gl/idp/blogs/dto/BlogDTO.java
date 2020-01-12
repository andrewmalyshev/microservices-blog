package com.gl.idp.blogs.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogDTO {
    private String id;
    private String description;
    private Integer userId;
    private boolean isApproved;
}
