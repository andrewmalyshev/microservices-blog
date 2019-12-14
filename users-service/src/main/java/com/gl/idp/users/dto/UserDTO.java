package com.gl.idp.users.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private int roleId;
    private String password;
    private String token;
    private boolean isEnabled;
}
