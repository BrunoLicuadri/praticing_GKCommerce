package com.practice.praticando.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.praticando.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
