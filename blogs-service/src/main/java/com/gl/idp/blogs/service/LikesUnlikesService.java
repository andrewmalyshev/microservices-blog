package com.gl.idp.blogs.service;

import com.gl.idp.blogs.dto.LikesUnlikesDTO;
import com.gl.idp.blogs.model.Blogs;
import com.gl.idp.blogs.model.LikesUnlikes;
import com.gl.idp.blogs.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikesUnlikesService {

    private final BlogRepository blogRepository;
    private final BlogService blogService;

    public Object addLikesUnlikes(LikesUnlikesDTO likesUnlikesDTO, String authToken) {

        String blogId = likesUnlikesDTO.getBlogId();

        if (blogService.getById(blogId) != null) {

            Blogs blog = blogService.getById(blogId);

            if (blogService.isApprovedBlog(blog) && !blogService.isMyBlog(blog, authToken)) {

                int userId = likesUnlikesDTO.getUserId();
                String type = likesUnlikesDTO.getType();
                LikesUnlikes likesUnlikes = null;

                try {
                    likesUnlikes = blog.getLikesUnlikes().stream().filter(
                            t -> type.equals(t.getType()) &&
                                    blogId.equals(t.getBlogId()) &&
                                    userId == t.getUserId()).findFirst().get();
                } catch (Exception e) {
                }

                if (likesUnlikes == null) {
                    likesUnlikes = getLikesUnlikesEntityFromDTO(likesUnlikesDTO, blog);
                    blog.getLikesUnlikes().add(likesUnlikes);
                    blogRepository.save(blog);

                    LikesUnlikes alternativeLikesUnlikes = null;

                    String alternateType = "ul";
                    if (likesUnlikesDTO.getType().equals("ul"))
                        alternateType = "l";

                    try {
                        String finalAlternateType = alternateType;
                        alternativeLikesUnlikes = blog.getLikesUnlikes().stream().filter(
                                t -> finalAlternateType.equals(t.getType()) &&
                                        blogId.equals(t.getBlogId()) &&
                                        userId == t.getUserId()).findFirst().get();
                    } catch (Exception e) {
                    }

                    if (alternativeLikesUnlikes != null) {
                        blog.getLikesUnlikes().remove(alternativeLikesUnlikes);
                        blogRepository.save(blog);
                    }

                }

            }

        }

        return blogService.blogList(authToken);
    }

    public LikesUnlikes getLikesUnlikesEntityFromDTO(LikesUnlikesDTO likesUnlikesDTO, Blogs blog/*, User user*/) {
        LikesUnlikes likesUnlikes = new LikesUnlikes();
        likesUnlikes.setBlogId(likesUnlikesDTO.getBlogId());
        likesUnlikes.setUserId(likesUnlikesDTO.getUserId());
        likesUnlikes.setType(likesUnlikesDTO.getType());
//        likesUnlikes.setBlogByBlogId(blog);
//        likesUnlikes.setUserByUserId(user);
        return likesUnlikes;
    }
}
