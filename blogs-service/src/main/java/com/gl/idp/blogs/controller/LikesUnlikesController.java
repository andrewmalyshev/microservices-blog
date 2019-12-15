package com.gl.idp.blogs.controller;

import com.gl.idp.blogs.dto.LikesUnlikesDTO;
import com.gl.idp.blogs.service.LikesUnlikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/likesunlikes")
@RequiredArgsConstructor
public class LikesUnlikesController {

    private final LikesUnlikesService likesUnlikesService;

    @PostMapping(value = "/add")
    public Object add(@RequestHeader("Authorization") String authToken, @RequestBody LikesUnlikesDTO likesUnlikesDTO){
        return likesUnlikesService.addLikesUnlikes(likesUnlikesDTO, authToken);
    }
}
