package com.gl.idp.blogs.repository;

import com.gl.idp.blogs.model.Blogs;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends MongoRepository<Blogs, String> {
    List<Blogs> findByApproved(boolean isApproved);
    List<Blogs> findByUserIdAndApproved(Integer userId, boolean isApproved);
    Optional<Blogs> findById(String id);

}
