package com.discounts.discountsprice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.discounts.discountsprice.dataconverter.ProductDataConverter;
import com.discounts.discountsprice.model.PriceLabel;
import com.discounts.discountsprice.model.ProductModel;
import com.discounts.discountsprice.repository.DiscountPriceRepository;

@Service
class DiscountPriceServiceImpl implements DiscountPriceService{

	@Autowired
	private DiscountPriceRepository discountRepository;

	@Override
	public List<ProductModel> getDiscountedProducts(Integer categoryId, Optional<PriceLabel> priceLabelType) {
		
		ProductDataConverter products = new ProductDataConverter();
		
		List<ProductModel> productModels = discountRepository.getDiscountedProducts(categoryId).stream().map(product ->
								{ return products.convert(product, priceLabelType);}
						).collect(Collectors.toList());
		
		return productModels;
	}
}
