package com.masai.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.masai.DTO.OrderDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer bookingOrderId;

	private Integer customerId;

	private LocalDateTime orderDate;

	@NotBlank(message = "Order status can not be blank")
	@NotNull(message = "Order status can not be null")
	private String orderStatus;

	@NotBlank(message = "Payment status can not be blank")
	@NotNull(message = "Payment status can not be null")
	private String paymentStatus;

	@NotBlank(message = "Transaction mode can not be blank")
	@NotNull(message = "Transaction mode can not be null")
	private String TransactionMode;

	private Integer quantity;

	private Double totalCost;

	@Embedded
	@ElementCollection
	private List<OrderDTO> products = new ArrayList<>();

	public Orders(LocalDateTime orderDate, String orderStatus, String paymentStatus,
			@NotBlank(message = "Transaction mode can not be blank") @NotNull(message = "Transaction mode can not be null") String transactionMode,
			Integer quantity, Double totalCost) {
		super();
		this.orderDate = orderDate;
		this.orderStatus = orderStatus;
		this.paymentStatus = paymentStatus;
		TransactionMode = transactionMode;
		this.quantity = quantity;
		this.totalCost = totalCost;
	}

}