package com.masai.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer customerId;
	
	@NotBlank(message = "First name can not be blank")
	@NotNull(message = "First name can not be null")
	private String firstName;
	
	@NotBlank(message = "Last name can not be blank")
	@NotNull(message = "Last name can not be null")
	private String lastName;
	
	@Email(message = "Please provide a valid email address")
	private String email;
	
	@NotBlank(message = "Phone number can not be blank")
	@Pattern(regexp = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$",message = "Please provide a valid phone number")
	@Column(unique = true)
	@NotNull(message = "Phone number can not be null")
	private String phone;

	@Size(min = 8 ,message= "Please provide a valid password of minimum 8 characters")
	@NotNull(message = "Password can not be null")
	private String password;
	
	
	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	private List<Address> addresses=new ArrayList<>();
	
	@JsonIgnore
	private LocalDateTime dateRegistered;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private Cart cart = new Cart();


}