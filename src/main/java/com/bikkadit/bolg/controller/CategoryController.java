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
import com.bikkadit.bolg.payloads.CategoryDto;
import com.bikkadit.bolg.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	

	/**
	 * @author RushikeshHatkar
	 * @apiNote create category 
	 * @param categoryDto
	 * @return
	 * @since 1.0v
	 */
	@PostMapping("/category")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		log.info("Entering Request for the save category data:");
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
		log.info("completed Request for the save category data:");
		return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);
	}

	/**
	 * @author RushikeshHatkar
	 * @apiNote update category
	 * @param categoryDto
	 * @param catId
	 * @return
	 * @since 1.0v
	 */

	@PutMapping("/category/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable Integer catId) {
		log.info("Entering Request for the update category data:{}",catId);
		CategoryDto updatedCat = this.categoryService.updateCategory(categoryDto, catId);
		log.info("completed Request for the update category data:{}",catId);
		return new ResponseEntity<CategoryDto>(updatedCat, HttpStatus.OK);
	}

	/**
	 * @author RushikeshHatkar
	 * @apiNote delete category using ID
	 * @param catId
	 * @return
	 */

	@DeleteMapping("/category/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId) {
		log.info("Entering Request for the delete category data:{}",catId);
		this.categoryService.deleteCategory(catId);
		log.info("completed Request for the delete category data:{}",catId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(AppConstants.DELETE,true),
				HttpStatus.OK);

	}

	/**
	 * @author RusikeshHatkar
	 * @apiNote get category by using Id
	 * @param catId
	 * @return
	 * @since 1.0v
	 */

	@GetMapping("/category/{catId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId) {
		log.info("Entering Request for the get category data:{}",catId);

		CategoryDto categoryDto = this.categoryService.getCategory(catId);
		log.info("Completed Request for the get category data:{}",catId);
		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
	}

	/**
	 * @author RushikeshHatkar
	 * @apiNote get all categories
	 * @return
	 * @since 1.0v
	 */

	@GetMapping("/categories")
	public ResponseEntity<List<CategoryDto>> getCategories() {
		log.info("Entering Request for the get category data:");
		List<CategoryDto> categories = this.categoryService.getCategorys();
		log.info("Entering Request for the get category data:");
		return new ResponseEntity<List<CategoryDto>>(categories, HttpStatus.OK);
	}

}
