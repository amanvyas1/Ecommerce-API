package com.masai.DTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDTO {
	
	@Id
	private Integer productId;

	private String productName;

	private Double salePrice;

	private Double marketPrice;

	private String color;

	private String brand;

	@Min(1)
	private Integer quantity;

	private String categoryName;

	private String subCategory;

	private String description;

}