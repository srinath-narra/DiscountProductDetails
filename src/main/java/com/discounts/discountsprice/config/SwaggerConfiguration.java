package com.discounts.discountsprice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {
	
	@Autowired(required= false)
	private BuildProperties buildProperties;
	
	@Bean
	public Docket api(){
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getApiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.discounts.discountsprice.controller"))
				.paths(PathSelectors.any())
				.build();
	}
	
	private ApiInfo getApiInfo(){
		Contact contact = new Contact("Products Category","www.syst.apigee.net","syst@apigee.net");
		String version ="1.0.0";
		if(buildProperties!=null){
			version = buildProperties.getVersion();
		}
		
		return new ApiInfoBuilder()
				.title("Discount Products MicroService")
				.description("Discount Products MicroService")
				.version(version)
				.license("")
				.contact(contact)
				.build();
	}

}
