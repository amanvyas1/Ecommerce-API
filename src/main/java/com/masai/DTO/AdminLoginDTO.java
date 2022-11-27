package com.masai.DTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoginDTO {
	@NotNull(message = "Phone number can not be null")
	@NotBlank(message = "Phone number can not be blank")
	private String phone;
	
	@NotNull(message = "Password can not be null")
	@NotBlank(message = "Password can not be blank")
	private String password;
}
