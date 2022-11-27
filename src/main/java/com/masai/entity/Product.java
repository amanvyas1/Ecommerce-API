package com.masai.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
	
	@NotBlank(message = "Product name can not be blank")
	private String productName;
	
	@Min(value = 0, message = "Product sale price can not be negative value")
	private Double salePrice;
	
	@Min(value = 0, message = "Product price can not be negative value")
	private Double marketPrice;
	
	@NotBlank(message = "Color can not be blank")
	private String color;
	
	@NotBlank(message = "Brand name can not be blank")
	@NotNull(message = "Brand name can not be null")
	private String brand;
	
	@Min(value = 1, message = "Stock should not be less than 1")
	private Integer stock;
	
	@NotBlank(message = "Subcategory can not be blank")
	private String subCategory;
	
	@NotBlank(message = "Description should not be blank")
	private String description;
	
	@Valid
	@ManyToOne(cascade = CascadeType.ALL)
	private Category category;
		
}
