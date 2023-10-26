package com.bikkadit.bolg.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bikkadit.bolg.constants.AppConstants;
import com.bikkadit.bolg.payloads.ApiResponse;
import com.bikkadit.bolg.payloads.PostDto;
import com.bikkadit.bolg.payloads.PostResponse;
import com.bikkadit.bolg.service.FileService;
import com.bikkadit.bolg.service.PostService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	// create

	/**
	 * @author RushikeshHatkar
	 * @apiNote create post
	 * @param postDto
	 * @param userId
	 * @param categoryId
	 * @return
	 * @since 1.0v
	 */
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		log.info("Entering Request for the save post data:");
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		log.info("Completed Request for the save post data:");
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);

	}

	// get by user

	/**
	 * @author RushikeshHatkar
	 * @apiNote get post by userId
	 * @param userId
	 * @return
	 * @since 1.0v
	 */
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByuser(@PathVariable Integer userId) {
		log.info("Entering Request for the get post data:{}", userId);
		List<PostDto> posts = this.postService.getPostByUser(userId);
		log.info("Completed Request for the get post data:{}", userId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

	}

	// get by category
	/**
	 * @author RushikeshHatkar
	 * @apiNote get post by categoryId
	 * @param categoryId
	 * @return
	 * @since 1.0v
	 */

	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {
		log.info("Entering Request for the save post data:{}", categoryId);
		List<PostDto> posts = this.postService.getPostByCategory(categoryId);
		log.info("Completed Request for the get post data:{}", categoryId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

	}

	/**
	 * @author RushikeshHatkar
	 * @apiNote get post by id
	 * @param postId
	 * @return
	 * @since 1.0v
	 */
	@GetMapping("/post/postId/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
		log.info("Entering Request for the get post data:{}", postId);
		PostDto postById = this.postService.getPostById(postId);
		log.info("Completed Request for the get post data:{}", postId);
		return new ResponseEntity<PostDto>(postById, HttpStatus.OK);

	}

	// get all posts

	/**
	 * @author RushikeshHatkar
	 * @apiNote get all post
	 * @return
	 * @since 1.0v
	 */

	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

		log.info("Entering Request for the get post data:");
		PostResponse allPost = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		log.info("Completed Request for the get post data:");
		return new ResponseEntity<PostResponse>(allPost, HttpStatus.OK);

	}

	// update post

	/**
	 * @author RushikeshHatkar
	 * @apiNote update post by using postId
	 * @param postDto
	 * @param postId
	 * @return
	 * @since 1.0v
	 */

	@PutMapping("/postId/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		log.info("Entering Request for the update post data:{}", postId);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		log.info("Completed Request for the update post data:{}", postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);

	}

	/**
	 * @author RushikeshHatkar
	 * @apiNote delete post by using PostId
	 * @param postId
	 * @return
	 * @since 1.0v
	 */
	@DeleteMapping("/postId/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) {
		log.info("Entering Request for the delete post data:{}", postId);
		this.postService.deletePost(postId);
		log.info("Completed Request for the delete post data:{}", postId);
		return new ApiResponse(AppConstants.DELETE, true);

	}

	// search
	/**
	 * @author RushikeshHatkar
	 * @apiNote searching data
	 * @param keyword
	 * @return
	 * @since 1.0 v
	 */
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keyword") String keyword) {
		log.info("Entering Request for the searching post data:{}");
		List<PostDto> result = this.postService.searchPosts(keyword);
		log.info("Completed Request for the Searching post data:{}");
		return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);
	}

	// image upload

	@PostMapping("posts/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException {

		PostDto postDto = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}

	@GetMapping(value = "/posts/image/{imgName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void doenloadImage(@PathVariable String imgName, HttpServletResponse response) throws IOException {

		InputStream resource = this.fileService.getResource(path, imgName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());

	}

}