package com.gl.idp.blogs.service;

import com.gl.idp.blogs.client.UsersServiceClient;
import com.gl.idp.blogs.dto.CommentDTO;
import com.gl.idp.blogs.model.Blogs;
import com.gl.idp.blogs.model.Comments;
import com.gl.idp.blogs.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final BlogService blogService;
    private final CommentRepository commentRepository;
    private final UsersServiceClient usersServiceClient;

    public Object save(CommentDTO commentDTO, String authToken){
        Blogs blog = blogService.getById(commentDTO.getBlogId());
        int userId = usersServiceClient.getUserIdFromToken(authToken);
        if(blog.isApproved()) {
            Comments comments = new Comments();
            comments.setBlogId(commentDTO.getBlogId());
            comments.setDescription(commentDTO.getDescription());
            comments.setUserId(userId);
            comments.setBlogByBlogId(blog);
            comments.setUsername(usersServiceClient.getUserName(userId));
            commentRepository.save(comments);
        }
        return blogService.blogList(authToken);
    }
}
