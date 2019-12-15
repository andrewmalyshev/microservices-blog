package com.gl.idp.blogs.repository;

import com.gl.idp.blogs.model.Blogs;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BlogRepository extends CrudRepository<Blogs, Integer> {
    List<Blogs> findByIsApproved(boolean isApproved);
    List<Blogs> findByUserIdAndIsApproved(int userId, boolean isApproved);
    Blogs findById(int id);

}
