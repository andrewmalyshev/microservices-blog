package com.gl.idp.users.controller;

import com.gl.idp.users.dto.UserDTO;
import com.gl.idp.users.service.UpUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/users/")
@RequiredArgsConstructor
public class UserController {

    private final UpUserService upUserService;

    @GetMapping("/list")
    public Object userList(HttpServletRequest request){
        return upUserService.getAllUsers();
    }

    @PostMapping(value = "/is_unique_email")
    public Object UniqueEmailChecker(@RequestBody UserDTO userDTO){
        return upUserService.isUniqueEmail(userDTO.getEmail());
    }

    @PostMapping("/add")
    public Object save(@RequestBody UserDTO userDTO) throws Exception {
        return upUserService.saveUser(userDTO, 2);
    }

    @PostMapping("/admin/add")
    public Object saveAdmin(@RequestBody UserDTO userDTO) throws Exception {
        return upUserService.saveUser(userDTO, 1);
    }

    @PostMapping(value = "/change-approval")
    public Object changeApproval(@RequestBody UserDTO userDTO){
        return upUserService.changeApproval(userDTO);
    }

}
