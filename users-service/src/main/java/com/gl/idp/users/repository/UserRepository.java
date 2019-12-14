package com.gl.idp.users.repository;


import com.gl.idp.users.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findById(int id);
    Optional<User> findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
    User findByEmailAndRoleId(String email, int roleId);
    List<User> findByRoleId(int roleId);
}
