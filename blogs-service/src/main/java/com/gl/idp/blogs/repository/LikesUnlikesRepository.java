package com.gl.idp.blogs.repository;

import com.gl.idp.blogs.model.LikesUnlikes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesUnlikesRepository extends CrudRepository<LikesUnlikes, Integer> {
    LikesUnlikes findByUserIdAndBlogIdAndType(int userId, int blogId, String type);
}
