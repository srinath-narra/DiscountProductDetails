package com.discounts.discountsprice.dataconverter;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.discounts.discountsprice.domain.ColorSwatch;
import com.discounts.discountsprice.domain.Price;
import com.discounts.discountsprice.domain.Product;
import com.discounts.discountsprice.model.ColorSwatchModel;
import com.discounts.discountsprice.model.PriceLabel;
import com.discounts.discountsprice.model.ProductModel;
import com.discounts.discountsprice.util.ColorToRex;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProductDataConverter {
	
	enum PriceType{
		Then1,Then2,Now
	}
	
	public ProductModel convert(Product source, Optional<PriceLabel> labelType) {
		
		if(source==null)
			return null;
		
		String priceLabel = printPriceLabel(labelType, source.getPrice());
		
		log.info("product id {}", source.getProductId());
		
		
		ProductModel target = new ProductModel();
		
		target.setId(source.getProductId());
		target.setTitle(source.getTitle());
		target.setPriceLabel(priceLabel);
		target.setNowPrice(nowPrice(source.getPrice()));
		
		target.setColorSwatches(
				source.getColorSwatches().stream().map(x -> {return getColorSwatchModel(x);}
						).collect(Collectors.toList())
			);
		
		log.info("product {}", target);
		
		return target;
		
	}
	
	
	
	
	private String printPriceLabel(Optional<PriceLabel> labelType, Price price) {
		
		String response="";
		
		PriceLabel priceLabel = labelType.map(x -> {
									return x;
								}).orElse(PriceLabel.ShowWasNow);
		
		
		if(PriceLabel.ShowWasNow.equals(priceLabel)) {
			
			response = price.getWas().map( x -> {
				return "Was " +price.getCurrency().getResponse()+x+", now "+nowPrice(price);
			}).orElse("Was "+nowPrice(price)+", now "+nowPrice(price));
		
		}
		else if(PriceLabel.ShowWasThenNow.equals(priceLabel)){ 
			log.info("ShowWasThenNow",priceLabel);
			response = price.getThen2().map(x -> {
				return "Was " +price.getCurrency().getResponse()+x+""+  price.getWas() +", then2 "+ thenPrice(price) +", now "+nowPrice(price);
			}).orElse("Was " +price.getCurrency().getResponse()+""+ price.getWas()+", then "+ price.getThen1() +", now "+nowPrice(price));
		}
		else if(PriceLabel.ShowPercDscount.equals(priceLabel)) {
			response = price.getWas().map( x -> {
				return "" +price.getCurrency().getResponse()+x+""+calculateDiscountPersentange(nowPrice(price),price.getWas());
			}).orElse("Was "+nowPrice(price)+", now "+nowPrice(price));
			
		}
		

		
		return response;
	}
	

	private String calculateDiscountPersentange(String nowPrice, Optional<Float> beforePrice) {
		if(beforePrice.isPresent()){
			Float value = beforePrice.get();
			return "Discount Price:"+((Float.valueOf(nowPrice.substring(1))-value)/value)*100;
		}
		else
		{
			return "Discount Price:";
		}
		
	}
	
	private ColorSwatchModel getColorSwatchModel(ColorSwatch colorSwatch){
		if(colorSwatch==null)
			return null;
		
		ColorSwatchModel target = new ColorSwatchModel();
		
		target.setColor(colorSwatch.getColor());
		target.setSkuid(colorSwatch.getSkuId());
		target.setRgbColor(ColorToRex.stringToColorCustom(colorSwatch.getBasicColor()));
		
		return target;
	}
	
	
	private String thenPrice(Price price) {
		Float then1Then2;
		if(price.getThen1().isPresent())
			then1Then2 = price.getThen1().get().floatValue();
			else
			then1Then2 = price.getThen2().isPresent()?price.getThen2().get().floatValue():Float.parseFloat((String)price.getNow());
		
		return then1Then2.toString();
	}
	
	private String nowPrice(Price price) {
		Float nowPrice;
		
		try {
			nowPrice = Float.parseFloat((String)price.getNow());
		}catch (Exception e) {
			nowPrice = 1.23f;
		}	
		return nowPrice <10 ? price.getCurrency().getResponse()+Math.round(nowPrice) : price.getCurrency().getResponse()+ nowPrice ;
	}
	
	private String priceConversion(Price price){
		Float nowPrice;
		
		try {
			nowPrice = price.getThen1().get();
		}catch (Exception e) {
			nowPrice = 1.23f;
		}	
		return nowPrice <10 ? price.getCurrency().getResponse()+Math.round(nowPrice) : price.getCurrency().getResponse()+ nowPrice ;
	
	}	

}
