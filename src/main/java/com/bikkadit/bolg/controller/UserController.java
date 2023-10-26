package com.bikkadit.bolg.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bikkadit.bolg.constants.AppConstants;
import com.bikkadit.bolg.payloads.ApiResponse;
import com.bikkadit.bolg.payloads.UserDto;
import com.bikkadit.bolg.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * @author RushikeshHatkar
	 * @apiNote for create users
	 * @exception
	 * @param userDto
	 * @return
	 * @since 1.0v
	 */
	@PostMapping("/users")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		
		log.info("Entering Request for the save user data:");
		UserDto createUser = this.userService.createUser(userDto);
		log.info("completed Request for the save user data:");
		return new ResponseEntity<>(createUser, HttpStatus.CREATED);
	}

	/**
	 * @author RushiHatkar
	 * @apiNote for update single user
	 * @exception
	 * @param userId
	 * @return
	 * @since 1.0v
	 */
	@PutMapping("/users/{userId}")
	
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId) {
		log.info("Entering Request for the update user data :{}",userId);

		UserDto updateUser = this.userService.updateUser(userDto, userId);
		log.info("completed Request for the update user data :{}:",userId);

		return new ResponseEntity<UserDto>(updateUser, HttpStatus.OK);
	}

	/**
	 * @author RushikeshHatkar
	 * @apiNote for delete single user
	 * @exception
	 * 
	 * @param userId
	 * @return
	 * @since 1.0v
	 */
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
		log.info("Entering Request for the delete user data :{}",userId);
		this.userService.deleteUser(userId);
		log.info("Entering Request for the delete user data:{}",userId);
		return new ResponseEntity<>(new ApiResponse(AppConstants.DELETE,true), HttpStatus.OK);

	}

	/**
	 * @author RushikeshHatkar
	 * @apiNote get all users
	 * @exception
	 * @return
	 */
	@GetMapping("usres/getAll")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		log.info("Entering request for the get user data");
		List<UserDto> allUsers = this.userService.getAllUsers();
		log.info("Completed request for the get user data");
		return new ResponseEntity<>(allUsers,HttpStatus.OK);

	}
	
	/**
	 * @author RushikeshHatkar
	 * @apiNote get single user 
	 * @param userId
	 * @return
	 */

	@GetMapping("/users/{userId}")
	public ResponseEntity<UserDto> getAllUsers(@PathVariable Integer userId) {
		log.info("Entering Request for the get user data :{}",userId);
		UserDto userById = this.userService.getUserById(userId);
		log.info("Completed Request for the get user data :{}",userId);
		return new ResponseEntity<>(userById,HttpStatus.OK);

	}
}
