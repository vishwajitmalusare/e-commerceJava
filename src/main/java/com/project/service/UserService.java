package com.project.service;

import java.util.List;
import java.util.Optional;

import com.project.exception.AlreadyExistException;
import com.project.exception.RecordNotFoundException;
import com.project.model.User;

public interface UserService {

	User createUser(User user)throws AlreadyExistException;
	
    User getUserById(Long userId);

    List<User> getAllUsers();

    User updateUser(User user);

    void deleteUser(Long userId);
    
    void deleteAllUser();

	User loginUser(User user);
	
	User registerUser(User user);
	
	Optional<User> authenticateUser(String email, String Password) throws RecordNotFoundException;
}
