package com.bikkadit.bolg.service;

import java.util.List;

import com.bikkadit.bolg.entity.Post;
import com.bikkadit.bolg.payloads.PostDto;
import com.bikkadit.bolg.payloads.PostResponse;

public interface PostService {

	//create 
	
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	
	// Update 
	
	PostDto updatePost(PostDto postDto,Integer postId);
	
	// delete 
	
	void deletePost(Integer postId);
	
	// get by id
	
	PostDto getPostById(Integer postId);
	
	//getAll
	
	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	// get all post by category
	
	List<PostDto> getPostByCategory(Integer categoryId);
	
	// get all post by user
	
	List<PostDto> getPostByUser(Integer userId);
	
	// search posts
	List<PostDto> searchPosts(String keyword);
}
