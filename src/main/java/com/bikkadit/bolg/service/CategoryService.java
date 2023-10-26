package com.bikkadit.bolg.service;

import java.util.List;

import com.bikkadit.bolg.payloads.CategoryDto;

public interface CategoryService {

	//create
	
	 CategoryDto createCategory(CategoryDto categoryDto);
	
	//Update
	
	 CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	
	//delete
	
	 void deleteCategory(Integer categoryId);
	
	//get
	
	 CategoryDto getCategory(Integer categoryId);
	 
	 // getAll
	 
	List<CategoryDto> getCategorys();
	
	

}
