package com.challenge.techforb.service;

import com.challenge.techforb.auth.dto.ResponseUserDTO;


public interface IUserService {
    ResponseUserDTO findById(Long userId);
}
