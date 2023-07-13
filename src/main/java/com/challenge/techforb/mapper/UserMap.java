package com.challenge.techforb.mapper;

import com.challenge.techforb.auth.service.JwtUtils;
import com. challenge.techforb.auth.dto.ResponseUserDTO;
import com.challenge.techforb.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMap {
    @Autowired
    private JwtUtils jwtUtils;


    public User userAuthDTO2Entity(ResponseUserDTO userDto) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        return user;

    }

    public ResponseUserDTO userAuthEntity2DTO(User entitySaved) {
        ResponseUserDTO dto = new ResponseUserDTO();
        dto.setId(entitySaved.getUserId());
        dto.setFirstName(entitySaved.getFirstName());
        dto.setLastName(entitySaved.getLastName());
        dto.setEmail(entitySaved.getEmail());
        return dto;
    }
}