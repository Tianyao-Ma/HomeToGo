package com.mty.hometogo.service;

import com.mty.hometogo.model.Authority;
import com.mty.hometogo.model.User;
import com.mty.hometogo.model.UserRole;
import com.mty.hometogo.repository.AuthorityRepository;
import com.mty.hometogo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService {

    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;
    @Autowired
    public RegisterService(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }
    // help maintain the atomic of db writing;
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void add(User user, UserRole role) {
        //jpa helps auto write into db
        userRepository.save(user);
        authorityRepository.save(new Authority(user.getUsername(), role.name()));
    }

}
