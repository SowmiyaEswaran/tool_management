package com.tool.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RentalAgreement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rentalId;
	private String toolCode;
	private String toolType;
	private String toolBrand;
	private int rentalDays;
	private LocalDate checkoutDate;
	private LocalDate dueDate;
	private Double dailyRentalCharge;
	private int chargeDates;
	private Double preDiscountCharge;
	private Double dicountPercent;
	private Double discountAmount;
	private Double finalCharge;
}
