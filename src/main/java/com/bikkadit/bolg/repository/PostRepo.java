package com.bikkadit.bolg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bikkadit.bolg.entity.Category;
import com.bikkadit.bolg.entity.Post;
import com.bikkadit.bolg.entity.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	
	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);

	List<Post> findByTitleContaining(String title);
}
