package com.bikkadit.bolg.service;

import java.util.List;

import com.bikkadit.bolg.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto commentDto,Integer commentId);
	
	void deleteComment(Integer commentId);
	
	CommentDto getCommentById(Integer commentId);
	
	List<CommentDto> getAllComments();
	
	
}
