package com.bikkadit.bolg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bikkadit.bolg.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
