package com.gl.idp.users.service;


import com.gl.idp.users.dto.UserDTO;
import com.gl.idp.users.model.CustomUserDetails;
import com.gl.idp.users.model.User;
import com.gl.idp.users.repository.RoleRepository;
import com.gl.idp.users.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UpUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EncryptionService encryptionService;

    public User saveUser(UserDTO userDTO, int roleId) throws Exception {
        User user = new User();
        if(roleId == 2) {
            user.setEnabled(false);
        } else {
            user.setEnabled(true);
        }
        user.setEmail(userDTO.getEmail());
        user.setRoleId(roleId);
        user.setPassword(encryptionService.encrypt(userDTO.getPassword()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setRoleByRoleId(roleRepository.findById(roleId));
        return userRepository.save(user);
    }

    public Object getAllUsers(){
        return userRepository.findAll();
    }
    public Object changeApproval(UserDTO userDTO){
        User user = userRepository.findById(userDTO.getId());
        if(user.getRoleId() == 2) {
            user.setEnabled(userDTO.isEnabled());
            userRepository.save(user);
        }
        return getAllUsers();
    }
    public Object isUniqueEmail(String email){
        return !userRepository.findByEmail(email).isPresent();
    }

    public User getLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return  (CustomUserDetails) authentication.getPrincipal();
    }

    public boolean currentUserIsAdmin(){
        return getLoggedInUser().getRoleId() == 1;
    }

    public User getByUserEmailAndPassword(String email, String password){
        return userRepository.findByEmailAndPassword(email, password);
    }

    public String getFullUserName(Integer userId){
        User user = userRepository.findById(userId);
        return user.getFirstName() + " " + user.getLastName();
    }

    public int getUserIdFromToken(String authToken){
        String email = Jwts.parser()
                .setSigningKey("JwtSecretKey".getBytes())
                .parseClaimsJws(authToken)
                .getBody().getSubject();
        return userRepository.findByEmail(email).get().getId();
    }

    public boolean isAdminFromToken(String authToken){
        Claims claims = Jwts.parser()
                .setSigningKey("JwtSecretKey".getBytes())
                .parseClaimsJws(authToken)
                .getBody();
        List<String> authorities = (List<String>) claims.get("authorities");
        return authorities.stream().findFirst().get().equals("ROLE_ADMIN");
    }


}
