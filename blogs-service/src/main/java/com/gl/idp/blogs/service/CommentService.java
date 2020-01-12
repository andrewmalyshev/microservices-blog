package com.gl.idp.blogs.service;

import com.gl.idp.blogs.client.UsersServiceClient;
import com.gl.idp.blogs.dto.CommentDTO;
import com.gl.idp.blogs.model.Blogs;
import com.gl.idp.blogs.model.Comments;
import com.gl.idp.blogs.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BlogService blogService;
    private final BlogRepository blogRepository;
    private final UsersServiceClient usersServiceClient;

    public Object save(CommentDTO commentDTO, String authToken){
        Blogs blog = blogService.getById(commentDTO.getBlogId());
        int userId = usersServiceClient.getUserIdFromToken(authToken);
        if(blog.isApproved()) {
            Comments comments = new Comments();
            comments.setBlogId(commentDTO.getBlogId());
            comments.setDescription(commentDTO.getDescription());
            comments.setUserId(userId);
            comments.setUsername(usersServiceClient.getUserName(userId));
            blog.getComments().add(comments);
            blogRepository.save(blog);
        }
        return blogService.blogList(authToken);
    }
}
