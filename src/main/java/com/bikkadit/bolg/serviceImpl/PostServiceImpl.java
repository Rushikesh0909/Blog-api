package com.bikkadit.bolg.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bikkadit.bolg.constants.AppConstants;
import com.bikkadit.bolg.entity.Category;
import com.bikkadit.bolg.entity.Post;
import com.bikkadit.bolg.entity.User;
import com.bikkadit.bolg.exception.ResourceNotFoundException;
import com.bikkadit.bolg.payloads.PostDto;
import com.bikkadit.bolg.payloads.PostResponse;
import com.bikkadit.bolg.repository.CategoryRepo;
import com.bikkadit.bolg.repository.PostRepo;
import com.bikkadit.bolg.repository.UserRepo;
import com.bikkadit.bolg.service.PostService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		log.info("Entering dao call for save the post data with userId:{}And", userId, categoryId);

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + userId));

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + categoryId));

		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		Post newPost = this.postRepo.save(post);
		log.info("Completed dao call for save the post data with userId:{}And", userId, categoryId);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		log.info("Entering dao call for update the post data with :{}", postId);
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());

		post.setImageName(postDto.getImageName());

		Post updated = this.postRepo.save(post);
		log.info("Completed dao call for update the post data with :{}", postId);
		return this.modelMapper.map(updated, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		log.info("Entering dao call for delete the post data with :{}", postId);
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + postId));
		this.postRepo.delete(post);
		log.info("Completed dao call for update the post data with :{}", postId);

	}

	@Override
	public PostDto getPostById(Integer postId) {
		log.info("Entering dao call for get the post data with :{}", postId);
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + postId));
		log.info("Completed dao call for get the post data with :{}", postId);
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		log.info("Entering dao call for get the post data :{}");

		Sort sort = (sortDir.equalsIgnoreCase("asending")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> findAll = this.postRepo.findAll(p);

		List<Post> allposts = findAll.getContent();

		List<PostDto> collect = findAll.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		log.info("Completed dao call for get the post data :{}");

		PostResponse postResponse = new PostResponse();

		postResponse.setContent(collect);
		postResponse.setPageNumber(findAll.getNumber());
		postResponse.setPageSize(findAll.getSize());
		postResponse.setTotalElements(findAll.getTotalElements());
		postResponse.setTotalPages(findAll.getTotalPages());
		postResponse.setLastPage(findAll.isLast());
		return postResponse;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		log.info("Entering dao call for get the post data with category :{}", categoryId);

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + categoryId));
		List<Post> posts = this.postRepo.findByCategory(category);

		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		log.info("Completed dao call for get the post data with category :{}", categoryId);

		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		log.info("Entering dao call for get the post data with userId :{}", userId);
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + userId));
		List<Post> posts = this.postRepo.findByUser(user);

		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		log.info("Completed dao call for get the post data with userId :{}", userId);

		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		log.info("Entering dao call for search postBy title :{}");
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		log.info("Completed dao call for search postBy title :{}");
		return postDtos;
	}

}
