package com.masai.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer productId;
	
	@JsonIgnore
	private Integer AdminId;
	
	@NotBlank(message = "Product name can not be blank")
	private String productName;
	
	@NotNull(message = "sale price can not be null")
	@Min(value = 0, message = "Product sale price can not be negative value")
	private Double salePrice;
	
	@Min(value = 0, message = "Product price can not be negative value")
	private Double marketPrice;
	
	@Max(value = 5, message = "Rating can not be more than five")
	@Min(value = 1, message = "Rating can not be less than one")
	private Double rating;
	
	private Integer ratingCount;
	
	@NotBlank(message = "Color can not be blank")
	private String color;
	
	@NotBlank(message = "Brand name can not be blank")
	@NotNull(message = "Brand name can not be null")
	private String brand;
	
	@Min(value = 1, message = "Stock should not be less than 1")
	@NotNull(message = "Stock can not be null")
	private Integer stock;
	
	@NotBlank(message = "Subcategory can not be blank")
	private String subCategory;
	
	@NotBlank(message = "Description should not be blank")
	private String description;
	
	@NotNull(message = "Category id should not be null")
	private Integer categoryId;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Feedback> feedback = new ArrayList<>();
		
}
