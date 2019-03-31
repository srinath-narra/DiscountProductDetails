package com.discounts.discountsprice.model;

import java.util.List;

import com.discounts.discountsprice.domain.Product;

import lombok.Data;

@Data
public class CategoryModel {

	private List<Product> products;
}
