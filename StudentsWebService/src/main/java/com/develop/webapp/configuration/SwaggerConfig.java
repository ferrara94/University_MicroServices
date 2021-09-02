package com.develop.webapp.configuration;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	
	@Bean
	public Docket api() {
		/*
		 	it will be returned a documentations
		 	
		 	to access GUI use the url
		 	http://localhost:8080/swagger-ui.html 
		*/		
		return new Docket(DocumentationType.SWAGGER_2)
					.select()
						.apis(RequestHandlerSelectors.basePackage("com.develop.webapp.controller"))
						.paths(regex("/api/students.*"))
					.build()
					.apiInfo(apiInfo())
				;		
	}
	
	/*
 		to use swagger we need to add annotations @ in the concerned class
 		@Api()

	 	it allows us to insert information for who will use the APIs
	*/
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
					.title("Students Web Service")
					.description("REST APIs for Students")
					.version("1.0.0")
					.license("Ferrara S.p.a.")
					.contact(new Contact("Ferrara", "web_site", "ferrara_email"))
					.build()
				;
		
	}
	
}
