package com.blueyonder.ecomapp.service;

import com.blueyonder.ecomapp.model.UserPrincipal;
import com.blueyonder.ecomapp.model.UserRequest;
import com.blueyonder.ecomapp.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("Load UserByUsername method called with username: {}", username);
        UserRequest user = repo.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found for given user id - " + user.getUsername());
        }
//        return new User(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList("ROLE_" + user.getRole()));
        return UserPrincipal.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .authorities(AuthorityUtils.createAuthorityList("ROLE_" + user.getRole()))
                .build();
    }

    public boolean registerUser(UserRequest user) {
        UserRequest userRequest = repo.save(user);
        return userRequest != null ? true : false;
    }

    public Collection<UserRequest> getUsers() {
        Collection<UserRequest> users = repo.findAll();
        return users;
    }
}
