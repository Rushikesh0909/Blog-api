package com.bikkadit.bolg.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDto {

	
	private Integer categoryId;
	
	@NotBlank
	@Size(min = 4,message = "min size of category is 4")
	private String categoryTitle;
	
	@NotBlank
	@Size(min = 10,message = "min size of category is 10")
	private String categoryDescription;
}
