package com.check24.service;

import com.check24.domain.User;
import com.check24.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {


    private final UserRepository userRepository;

    @Autowired
    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {
        return userRepository.findAllByOrderByIdAsc().stream()
                .map(user -> new User(
                        user.getId(),
                        user.getUserName()
                )).collect(Collectors.toList());
    }

}
