package com.discounts.discountsprice.repository;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.discounts.discountsprice.domain.Product;
import com.discounts.discountsprice.model.CategoryModel;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Repository
public class DiscountPriceRepository {
	
	public List<Product> getDiscountedProducts(Integer categoryId) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		String resourceUrl = "https://jl-nonprod-syst.apigee.net/v1/categories/"+categoryId+"/products?key=2ALHCAAs6ikGRBoy6eTHA58RaG097Fma";
		
		ResponseEntity<CategoryModel> response  = restTemplate.exchange(resourceUrl ,HttpMethod.GET,	null,new ParameterizedTypeReference<CategoryModel>(){});
		
		if(response.getStatusCode().is2xxSuccessful())
			log.info("Product values.",response.getBody().getProducts());
		
		
		return response.getBody().getProducts();
	}
	

}
