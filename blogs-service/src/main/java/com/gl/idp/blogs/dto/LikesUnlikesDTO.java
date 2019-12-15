package com.gl.idp.blogs.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikesUnlikesDTO {
    private int id;
    private String type;
    private int userId;
    private int blogId;
}
