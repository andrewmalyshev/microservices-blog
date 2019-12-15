package com.gl.idp.blogs.service;

import com.gl.idp.blogs.dto.LikesUnlikesDTO;
import com.gl.idp.blogs.model.Blogs;
import com.gl.idp.blogs.model.LikesUnlikes;
import com.gl.idp.blogs.repository.BlogRepository;
import com.gl.idp.blogs.repository.LikesUnlikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikesUnlikesService {

    private final LikesUnlikesRepository likesUnlikesRepository;
    private final BlogRepository blogRepository;
    private final BlogService blogService;

    public Object addLikesUnlikes(LikesUnlikesDTO likesUnlikesDTO, String authToken){

        int blogId = likesUnlikesDTO.getBlogId();

        if(blogService.getById(blogId) != null){

            Blogs blog = blogService.getById(blogId);

            if(blogService.isApprovedBlog(blog) && !blogService.isMyBlog(blog, authToken)) {

                int userId = likesUnlikesDTO.getUserId();
                String type = likesUnlikesDTO.getType();
                LikesUnlikes likesUnlikes = null;

                try {
                    likesUnlikes = likesUnlikesRepository.findByUserIdAndBlogIdAndType(userId, blogId, type);
                } catch (Exception e) {
                }

                if (likesUnlikes == null) {
//                    User user = userRepository.findById(userId);
                    likesUnlikes = getLikesUnlikesEntityFromDTO(likesUnlikesDTO, blog);
                    likesUnlikesRepository.save(likesUnlikes);

                    LikesUnlikes alternativeLikesUnlikes = null;

                    String alternateType = "ul";
                    if(likesUnlikesDTO.getType().equals("ul"))
                        alternateType = "l";

                    try {
                        alternativeLikesUnlikes = likesUnlikesRepository.findByUserIdAndBlogIdAndType(userId, blogId, alternateType);
                    } catch (Exception e) {
                    }

                    if(alternativeLikesUnlikes != null){
                        likesUnlikesRepository.delete(alternativeLikesUnlikes);
                    }

                }

            }

        }

        return blogService.blogList(authToken);
    }

    public LikesUnlikes getLikesUnlikesEntityFromDTO(LikesUnlikesDTO likesUnlikesDTO, Blogs blog/*, User user*/){
        LikesUnlikes likesUnlikes = new LikesUnlikes();
        likesUnlikes.setBlogId(likesUnlikesDTO.getBlogId());
        likesUnlikes.setUserId(likesUnlikesDTO.getUserId());
        likesUnlikes.setType(likesUnlikesDTO.getType());
        likesUnlikes.setBlogByBlogId(blog);
//        likesUnlikes.setUserByUserId(user);
        return likesUnlikes;
    }
}
