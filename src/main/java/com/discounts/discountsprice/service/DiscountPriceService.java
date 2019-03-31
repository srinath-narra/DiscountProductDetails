package com.discounts.discountsprice.service;

import java.util.List;
import java.util.Optional;

import com.discounts.discountsprice.model.PriceLabel;
import com.discounts.discountsprice.model.ProductModel;


public interface DiscountPriceService {
	
	List<ProductModel> getDiscountedProducts(Integer categoryId, Optional<PriceLabel> priceLabelType);

}
