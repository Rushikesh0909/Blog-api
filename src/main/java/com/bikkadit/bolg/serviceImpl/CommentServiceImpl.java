package com.bikkadit.bolg.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bikkadit.bolg.constants.AppConstants;
import com.bikkadit.bolg.entity.Comment;
import com.bikkadit.bolg.entity.Post;
import com.bikkadit.bolg.exception.ResourceNotFoundException;
import com.bikkadit.bolg.payloads.CommentDto;
import com.bikkadit.bolg.payloads.UserDto;
import com.bikkadit.bolg.repository.CommentRepo;
import com.bikkadit.bolg.repository.PostRepo;
import com.bikkadit.bolg.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer commentId) {
		log.info("Entering dao call for create the comment:{}", commentId);

		Post post = this.postRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + commentId));

		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment save = this.commentRepo.save(comment);
		log.info("Completed dao call for create the comment:{}", commentId);
		return this.modelMapper.map(save, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		log.info("Entering dao call for delete the comment:{}", commentId);
		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + commentId));
		log.info("Entering dao call for delete the comment:{}", commentId);

		this.commentRepo.delete(comment);

	}

	@Override
	public CommentDto getCommentById(Integer commentId) {
		log.info("Entering dao call for get the comment:{}", commentId);

		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND + commentId));
		log.info("Completed dao call for get the comment:{}", commentId);

		return this.modelMapper.map(comment, CommentDto.class);
	}

	@Override
	public List<CommentDto> getAllComments() {
		log.info("Entering dao call for get the comments:");

		List<Comment> comments = this.commentRepo.findAll();
		List<CommentDto> Allcomment = comments.stream()
				.map((comment) -> this.modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
		log.info("Completed dao call for get the comments:");

		return Allcomment;
	}

}
