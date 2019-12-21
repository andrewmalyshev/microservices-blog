package com.gl.idp.blogs.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("auth-users-service")
public interface UsersServiceClient {

    @GetMapping("/users/id/{token}")
    int getUserIdFromToken(@PathVariable("token")String authToken);

    @GetMapping("/users/is-admin/{token}")
    boolean isAdmin(@PathVariable("token")String authToken);

    @GetMapping("/users/get-name/{id}")
    String getUserName(@PathVariable("id")Integer id);

}
