package com.discounts.discountsprice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ColorSwatch {
	private String basicColor;
	private String skuId;
	private String color;
}
