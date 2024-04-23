package com.tool.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ToolChargeRequest {
    private String toolCode;
    private LocalDate checkoutDate;
    private int rentalDays;
    private double discount;

    // Getters and setters
}
