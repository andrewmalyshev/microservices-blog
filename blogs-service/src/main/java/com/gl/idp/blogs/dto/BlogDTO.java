package com.gl.idp.blogs.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogDTO {
    private int id;
    private String description;
    private int userId;
    private boolean isApproved;
}
