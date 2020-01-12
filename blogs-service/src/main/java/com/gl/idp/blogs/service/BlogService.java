package com.gl.idp.blogs.service;

import com.gl.idp.blogs.client.UsersServiceClient;
import com.gl.idp.blogs.dto.BlogDTO;
import com.gl.idp.blogs.model.Blogs;
import com.gl.idp.blogs.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final UsersServiceClient usersServiceClient;

    public Blogs SaveBlog(BlogDTO blogDTO, String authToken){
        Blogs blog = new Blogs();
        int id = usersServiceClient.getUserIdFromToken(authToken);
        blog.setUserId(id);
        blog.setUsername(usersServiceClient.getUserName(id));
        blog.setDescription(blogDTO.getDescription());
        blog.setApproved(false);
        return blogRepository.save(blog);
    }

    public Object deleteBlog(BlogDTO blogDTO, String authToken){
        Blogs blog = blogRepository.findById(blogDTO.getId()).get();
        if(isMyBlog(blog, authToken) || usersServiceClient.isAdmin(authToken))
            blogRepository.delete(blog);
        return blogList(authToken);
    }

    public boolean isMyBlog(Blogs blog, String authToken){
        int meId = usersServiceClient.getUserIdFromToken(authToken);
        return blog.getUserId() == meId;
    }

    public Blogs getById(String blogId){
        return blogRepository.findById(blogId).get();
    }

    public boolean isApprovedBlog(Blogs blog){
        return blog.isApproved();
    }

    public Object blogList(String authToken){
        if(usersServiceClient.isAdmin(authToken))
            return blogRepository.findAll();
        int currentUserId = usersServiceClient.getUserIdFromToken(authToken);
        return Stream.concat(blogRepository.findByApproved(true).stream(),
                blogRepository.findByUserIdAndApproved(currentUserId, false).stream())
                .collect(Collectors.toList());
    }
}
