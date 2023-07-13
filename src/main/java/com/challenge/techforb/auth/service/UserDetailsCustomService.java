package com.challenge.techforb.auth.service;

import com.challenge.techforb.auth.dto.AuthenticationRequest;
import com.challenge.techforb.auth.dto.AuthenticationResponse;
import com.challenge.techforb.auth.dto.ResponseUserDTO;
import com.challenge.techforb.auth.exception.RepeatedUsername;
import com.challenge.techforb.entity.User;
import com.challenge.techforb.mapper.UserMap;
import com.challenge.techforb.repository.IUserRepository;
import com.challenge.techforb.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsCustomService implements UserDetailsService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtTokenUtils;

    @Autowired
    UserMap userMap;

    @Autowired
    IUserRepository iUserRepository;

    @Autowired
    IAccountService accountService;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = iUserRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("username or password not found");
        }
        return UserDetailsImpl.build(user);
    }

    public ResponseUserDTO save(ResponseUserDTO userDTO) throws RepeatedUsername {

        User entity = this.userMap.userAuthDTO2Entity(userDTO);

        User userSearch = this.iUserRepository.findByEmail(userDTO.getEmail());
        if (userSearch != null){
            throw new RepeatedUsername("user already exist");
        }
        User entitySaved = this.iUserRepository.save(entity);

        ResponseUserDTO responseUserDto = userMap.userAuthEntity2DTO(entitySaved);

        this.accountService.addAccount(entitySaved.getEmail());

        return responseUserDto;
    }

    public AuthenticationResponse signIn(AuthenticationRequest authenticationRequest) {
        UserDetails userDetails;
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        userDetails = (UserDetails) authentication.getPrincipal();
        final String jwt = jwtTokenUtils.generateToken(userDetails);
        return new AuthenticationResponse(jwt);
    }
}
