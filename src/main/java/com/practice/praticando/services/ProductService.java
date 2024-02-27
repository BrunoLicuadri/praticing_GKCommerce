package com.practice.praticando.services;


import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.practice.praticando.dtos.CategoryDTO;
import com.practice.praticando.dtos.ProductDTO;
import com.practice.praticando.dtos.ProductMinDTO;
import com.practice.praticando.entities.Category;
import com.practice.praticando.entities.Product;
import com.practice.praticando.repositories.ProductRepository;
import com.practice.praticando.services.exceptions.DataBaseException;
import com.practice.praticando.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Product result = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Recurso não encontrado."));
		return new ProductDTO(result);
	}
	
	
	@Transactional(readOnly = true)
	public Page<ProductMinDTO> findAll(String name, Pageable pageable){
		Page<Product> result = repository.searchByName(name, pageable);
		return result.map(x-> new ProductMinDTO(x));
	}
	
	@Transactional
	public ProductDTO insert(ProductDTO dto){
		Product entity = new Product();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new ProductDTO(entity);
	}
	
	
	@Transactional
	public ProductDTO update (Long id, ProductDTO dto){
		try {
			Product entity = repository.getReferenceById(id);
			copyDtoToEntity(dto, entity);
			entity = repository.save(entity);
			return new ProductDTO(entity);
		}
		catch(EntityNotFoundException e){
			throw new ResourceNotFoundException("Recurso não localizado.");
		}
		
	}
	
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public void delete(Long id) {
		if (!repository.existsById(id)) {
			throw new ResourceNotFoundException("Recurso Inexistente.");
		}
		try {
			repository.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataBaseException("Falha de integridade referencial.");
		}
		
	}
	
	
	
	
	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		entity.setDescription(dto.getDescription());
		entity.setImgUrl(dto.getImgUrl());
		entity.setName(dto.getName());
		entity.setPrice(dto.getPrice());
		entity.getCategories().clear();
		for(CategoryDTO catDto : dto.getCategories()) {
			Category cat = new Category();
			cat.setId(catDto.getId());
			cat.setName(catDto.getName());
			entity.getCategories().add(cat);
		}
	}
	
}
