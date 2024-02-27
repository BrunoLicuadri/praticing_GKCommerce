package com.practice.praticando.dtos;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.practice.praticando.entities.Category;
import com.practice.praticando.entities.Product;


public class ProductDTO {
	
	private Long id;
	@NotBlank(message = "Campo Requerido.")
	@Size(min=3, max=80, message="Mínimo de 3 e Máximo de 80 caracteres.")
	private String name;
	
	@NotBlank(message = "Campo requerido.")
	@Size(min=10, message="Descrição mínima de 10 caracteres.")
	private String description;
	
	@NotNull(message="Campo obrigatório")
	@Positive(message="Preço deve ser positivo.")
	private Double price;
	private String imgUrl;
	
	@NotEmpty(message="Deve ter pelo menos uma categoria")
	private List<CategoryDTO> categories = new ArrayList<>();
	
	public ProductDTO(Long id, String name, String description, Double price, String imgUrl) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
	}
	
	public ProductDTO(Product entity) {
		id = entity.getId();
		name = entity.getName();
		description = entity.getDescription();
		price = entity.getPrice();
		imgUrl = entity.getImgUrl();
		
		for(Category cat : entity.getCategories()) {
			categories.add(new CategoryDTO(cat));
		}
	}


	public Long getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public String getDescription() {
		return description;
	}


	public Double getPrice() {
		return price;
	}


	public String getImgUrl() {
		return imgUrl;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}
	
	

}
