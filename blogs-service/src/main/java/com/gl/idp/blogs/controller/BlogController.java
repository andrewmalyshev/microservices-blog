package com.gl.idp.blogs.controller;

import com.gl.idp.blogs.dto.BlogDTO;
import com.gl.idp.blogs.model.Blogs;
import com.gl.idp.blogs.repository.BlogRepository;
import com.gl.idp.blogs.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogRepository blogRepository;
    private final BlogService blogService;

    @PostMapping(value = "/add")
    public Object saveBlog(@RequestHeader("Authorization") String authToken, @RequestBody BlogDTO blogDTO){
        //TODO: Replace substring() method
        return blogService.SaveBlog(blogDTO, authToken.substring(7));
    }
    @GetMapping(value = "/list_all")
    public Object list(@RequestHeader("Authorization") String authToken){
        return blogService.blogList(authToken);
    }

    @PostMapping(value = "/change-approval")
    public Object changeApproval(@RequestHeader("Authorization") String authToken, @RequestBody BlogDTO blogDTO){
        Blogs blog = blogRepository.findById(blogDTO.getId());
        blog.setApproved(blogDTO.isApproved());
        blogRepository.save(blog);
        //TODO: Replace substring() method
        return blogService.blogList(authToken.substring(7));
    }

    @PostMapping(value = "/delete")
    public Object delete(@RequestHeader("Authorization") String authToken, @RequestBody BlogDTO blogDTO){
        return blogService.deleteBlog(blogDTO, authToken);
    }


}
