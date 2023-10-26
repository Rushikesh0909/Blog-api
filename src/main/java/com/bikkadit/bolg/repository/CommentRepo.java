package com.bikkadit.bolg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bikkadit.bolg.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
