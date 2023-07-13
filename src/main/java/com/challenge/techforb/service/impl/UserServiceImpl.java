package com.challenge.techforb.service.impl;

import com.challenge.techforb.auth.dto.ResponseUserDTO;
import com.challenge.techforb.auth.exception.ParamNotFound;;
import com.challenge.techforb.entity.User;
import com.challenge.techforb.mapper.UserMap;
import com.challenge.techforb.repository.IUserRepository;
import com.challenge.techforb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository iUserRepository;
    @Autowired
    private UserMap userMap;


    @Override
    public ResponseUserDTO findById(Long userId) {
        User user = iUserRepository.findById(userId).orElseThrow(
                () -> new ParamNotFound("User ID Invalid"));
        ResponseUserDTO dto = userMap.userAuthEntity2DTO(user);
        return dto;
    }
}
