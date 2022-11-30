package com.masai.DTO;

import javax.persistence.Entity;
import javax.persistence.Id;

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

	private String color;

	private String brand;

	private Integer quantity;
	
	private String stockStatus;

}