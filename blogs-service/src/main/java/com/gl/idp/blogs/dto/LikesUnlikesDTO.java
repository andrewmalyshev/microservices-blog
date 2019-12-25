package com.gl.idp.blogs.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikesUnlikesDTO {
    private Integer id;
    private String type;
    private Integer userId;
    private Integer blogId;
}
