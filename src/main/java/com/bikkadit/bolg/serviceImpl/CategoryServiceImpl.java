package com.bikkadit.bolg.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bikkadit.bolg.constants.AppConstants;
import com.bikkadit.bolg.entity.Category;
import com.bikkadit.bolg.exception.ResourceNotFoundException;
import com.bikkadit.bolg.payloads.CategoryDto;
import com.bikkadit.bolg.repository.CategoryRepo;
import com.bikkadit.bolg.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		log.info("Entering dao call for create the category data:");
		Category cat = this.modelMapper.map(categoryDto, Category.class);
		Category addedCat = this.categoryRepo.save(cat);
		log.info("Complete dao call for create the category data:");
		return this.modelMapper.map(addedCat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		log.info("Entering dao call for update the category data:{}", categoryId);
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND+categoryId));

		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());

		Category updatedCat = this.categoryRepo.save(cat);
		log.info("Completed dao call for update the category data:{}", categoryId);
		return this.modelMapper.map(updatedCat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		log.info("Entering dao call for delete the category data:");
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND+categoryId));

		this.categoryRepo.delete(cat);
		log.info("Completed dao call for delete the category data:");

	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		log.info("Entering dao call for get the category data:{}", categoryId);
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException(AppConstants.NOT_FOUND+categoryId));
		log.info("Complete dao call for get the category data:{}", categoryId);
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategorys() {
		log.info("Entering dao call for get the category data:");
		List<Category> categories = this.categoryRepo.findAll();
		List<CategoryDto> catDtos = categories.stream().map((cat) -> this.modelMapper.map(cat, CategoryDto.class))
				.collect(Collectors.toList());
		log.info("Completed dao call for get the category data:");
		return catDtos;
	}

}
