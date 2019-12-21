package com.gl.idp.blogs.controller;

import com.gl.idp.blogs.dto.CommentDTO;
import com.gl.idp.blogs.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/add")
    public Object save(@RequestHeader("Authorization") String authToken, @RequestBody CommentDTO commentDTO){
        return commentService.save(commentDTO, authToken.substring(7));
    }
}
