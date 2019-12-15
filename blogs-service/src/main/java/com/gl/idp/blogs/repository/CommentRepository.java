package com.gl.idp.blogs.repository;

import com.gl.idp.blogs.model.Comments;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comments, Integer> {

}
