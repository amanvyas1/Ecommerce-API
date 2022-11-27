package com.masai.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer addressId;
	
	@NotBlank(message = "House number can not be blank")
	private String houseNo;
	
	@NotBlank(message = "Colony name can not be blank")
	private String colony;
	
	@NotBlank(message = "City name can not be blank")
	private String city;
	
	@NotBlank(message = "State name can not be blank")
	private String state;
	
	@Pattern(regexp = "[1-9]{1}[0-9]{5}",message = "Pincode must of 6 digits")
	@NotNull
	private String pincode;
	
	@JsonIgnore
	@ManyToOne(cascade=CascadeType.ALL)
	private Customer customer;
	
}