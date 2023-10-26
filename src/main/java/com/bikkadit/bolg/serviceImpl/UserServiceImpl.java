package com.bikkadit.bolg.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bikkadit.bolg.constants.AppConstants;
import com.bikkadit.bolg.entity.User;
import com.bikkadit.bolg.exception.ResourceNotFoundException;
import com.bikkadit.bolg.payloads.UserDto;
import com.bikkadit.bolg.repository.UserRepo;
import com.bikkadit.bolg.service.UserService;

import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) {
		log.info("Entering dao call for save the user data");
		User user = this.modelMapper.map(userDto, User.class);
		User saveUser = this.userRepo.save(user);
		log.info("completed dao call for save the user data");
		return this.modelMapper.map(saveUser, UserDto.class);

	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {

		log.info("Entering dao call for update the user data {}:", userId);

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND+userId));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());

		User updateUser = this.userRepo.save(user);
		log.info("completed dao call for update the user data {}:", userId);

		return this.modelMapper.map(updateUser, UserDto.class);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND+userId));

		return this.modelMapper.map(user, UserDto.class);
	}

	@Override
	public List<UserDto> getAllUsers() {

		log.info("Entering dao call for get the user data");

		List<User> users = this.userRepo.findAll();

		List<UserDto> collect = users.stream().map((user) -> this.modelMapper.map(user, UserDto.class))
				.collect(Collectors.toList());
		log.info("completed dao call for get the user data");
		return collect;
	}

	@Override
	public void deleteUser(Integer userId) {
		log.info("Entering dao call for delete the user data {}:", userId);
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND+userId));

		this.userRepo.delete(user);
		log.info("completed dao call for delete the user data {}:", userId);

	}

}
