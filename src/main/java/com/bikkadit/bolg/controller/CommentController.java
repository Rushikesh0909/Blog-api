package com.bikkadit.bolg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bikkadit.bolg.constants.AppConstants;
import com.bikkadit.bolg.payloads.ApiResponse;
import com.bikkadit.bolg.payloads.CommentDto;
import com.bikkadit.bolg.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class CommentController {

	@Autowired
	private CommentService commentService;

	/**
	 * @author RushikeshHatkar
	 * @apiNote create comment
	 * @param comment
	 * @param commentId
	 * @return
	 * @since 1.0v
	 */
	@PostMapping("/posts/{postId}/comment")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto comment, @PathVariable Integer postId) {
		log.info("Entering Request for the create comments:{}", postId);

		CommentDto createcomment = this.commentService.createComment(comment, postId);
		log.info("Entering Request for the create comments:{}", postId);

		return new ResponseEntity<CommentDto>(createcomment, HttpStatus.CREATED);

	}

	/**
	 * @author RushikestHatkar
	 * @apiNote delete comment by using id
	 * @param commentId
	 * @return
	 * @since 1.0v
	 */
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteCommentById(@PathVariable Integer commentId) {
		log.info("Entering Request for the delete comments:{}", commentId);

		this.commentService.deleteComment(commentId);
		log.info("Completed Request for the delete comments:{}", commentId);
		return new ResponseEntity<ApiResponse>((new ApiResponse(AppConstants.DELETE, true)), HttpStatus.OK);

	}

	/**
	 * @author RushikeshHatkar
	 * @apiNote get comment by id
	 * @param commentId
	 * @return
	 * @since 1.0v
	 */
	@GetMapping("/comments/{commentId}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable Integer commentId) {
		log.info("Entering Request for the get comments:{}", commentId);

		CommentDto commentDto = this.commentService.getCommentById(commentId);
		log.info("Completed Request for the get comments:{}", commentId);

		return new ResponseEntity<CommentDto>(commentDto, HttpStatus.OK);

	}

	/**
	 * @author RushikeshHatkar
	 * @apiNote get all comments
	 * @return
	 * @since 1.0v
	 */
	@GetMapping("/comments")
	public ResponseEntity<List<CommentDto>> getAllComments() {
		log.info("Entering Request for the get comments :");

		List<CommentDto> allComments = this.commentService.getAllComments();
		log.info("Completed Request for the get comments :");

		return new ResponseEntity<List<CommentDto>>(allComments, HttpStatus.OK);

	}

}
