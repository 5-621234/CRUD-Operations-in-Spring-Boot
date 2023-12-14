package com.ritik.CRUDOperation.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ritik.CRUDOperation.exception.UserNotFoundException;
import java.util.List;


import com.ritik.CRUDOperation.model.User;

import com.ritik.CRUDOperation.repository.UserRepository;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {
	
	 @Autowired
	    private UserRepository userRepository;

	    @PostMapping("/user")
	    User newUser(@RequestBody User newUser) {
	        return userRepository.save(newUser);
	    }

	    @GetMapping("/users")
	    List<User> getAllUsers() {
	        return userRepository.findAll();
	    }
	    
	    @GetMapping("/user/{id}")
	    User getUserById(@PathVariable Long id) {
	        return userRepository.findById(id)
	                .orElseThrow(() -> new UserNotFoundException(id));
	    }
	    
	    
	    @PutMapping("/updateuser/{id}")
	    User updateUser(@RequestBody User newUser, @PathVariable Long id) {
	        return userRepository.findById(id)
	                .map(user -> {
	                    user.setFirstname(newUser.getFirstname());
	                    user.setLastname(newUser.getLastname());
	                    user.setStreet(newUser.getStreet());
	                    user.setAddress(newUser.getAddress());
	                    user.setCity(newUser.getCity());
	                    user.setState(newUser.getState());
	                    user.setEmail(newUser.getEmail());
	                    user.setPhone(newUser.getPhone());
	                    return userRepository.save(user);
	                }).orElseThrow(() -> new UserNotFoundException(id));
	    }

	    @DeleteMapping("/deleteuser/{id}")
	    String deleteUser(@PathVariable Long id){
	        if(!userRepository.existsById(id)){
	            throw new UserNotFoundException(id);
	        }
	        userRepository.deleteById(id);
	        return  "User with id "+id+" has been deleted success.";
	    }



}
