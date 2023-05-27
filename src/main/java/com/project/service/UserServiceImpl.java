package com.project.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.config.AppConstants;
import com.project.exception.AlreadyExistException;
import com.project.exception.RecordNotFoundException;
import com.project.model.Role;
import com.project.model.User;
import com.project.repository.RoleRepo;
import com.project.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {

		@Autowired
		private UserRepository userRepo;
		
		@Autowired
		private PasswordEncoder passwordEncoder;
		
		@Autowired
		private ModelMapper modelMapper;
		
		@Autowired
		private RoleRepo roleRepo;

	    @Override
	    public User createUser(User user) {
	    	 Optional<User> user1 = userRepo.findByEmail(user.getEmail());
	    	 if(user1.isPresent()) {
	    		 throw new AlreadyExistException("User Already Exist!!!");
	    	 }
	        return userRepo.save(user);
	    }

	    @Override
	    public User getUserById(Long userId) {
	        Optional<User> optionalUser = userRepo.findById(userId);
	        return optionalUser.get();
	    }

	    @Override
	    public List<User> getAllUsers() {
	        return userRepo.findAll();
	    }

	    @Override
	    public User updateUser(User user) {
	        User existingUser = userRepo.findById(user.getId()).get();
	        existingUser.setFirstName(user.getFirstName());
	        existingUser.setLastName(user.getLastName());
	        existingUser.setEmail(user.getEmail());
	        existingUser.setPassword(user.getPassword());
	        User updatedUser = userRepo.save(existingUser);
	        return updatedUser;
	    }

	    @Override
	    public void deleteUser(Long userId) {
	        userRepo.deleteById(userId);
	    }

		@Override
		public User loginUser(User user) {
			// TODO Auto-generated method stub
			Optional<User> user1 = userRepo.findByEmailAndPassword(user.getEmail(), user.getPassword());
			
				return user1.orElseGet(null);
		}

		@Override
		public void deleteAllUser() {
			userRepo.deleteAll();
		}

		@Override
		public User registerUser(User userDto) {
			
			User user = modelMapper.map(userDto, User.class);
			
			user.setPassword(this.passwordEncoder.encode(user.getPassword()));
			
			Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
			
			user.getRoles().add(role);
			System.out.println("Email Password: "+userDto.getEmail());

			
			User newUser = this.userRepo.save(user);
			
			return newUser;
		}

		@Override
		public Optional<User> authenticateUser(String email, String Password) throws RecordNotFoundException {
			
			System.err.println(email+""+passwordEncoder.encode(Password));
			Optional<User> user = userRepo.findByEmailAndPassword(email, Password);
			
			System.err.println("User::"+user);
			if(user.isPresent()) {
				return user;
			}else {
				throw new RecordNotFoundException();
			}
		}
	}
