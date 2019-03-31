package com.discounts.discountsprice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.discounts.discountsprice.model.PriceLabel;
import com.discounts.discountsprice.model.ProductModel;
import com.discounts.discountsprice.service.DiscountPriceService;


import io.swagger.annotations.Api;




@RestController
@Api(value = "Discounted Products Categories")
public class ProductsDiscountPriceController {
	
		@Autowired
		private DiscountPriceService discountPriceService;
	
	 	@GetMapping(value = "/productsByCategoryId/{categoryId}")
		public List<ProductModel> getDiscountedProductsByCategoryId(@PathVariable(required = true) Integer categoryId,@RequestParam(required = false) PriceLabel priceLabel){
			
	 		Optional<PriceLabel> labelType = Optional.ofNullable(priceLabel);
			return discountPriceService.getDiscountedProducts(categoryId,labelType);
		}

	
}
