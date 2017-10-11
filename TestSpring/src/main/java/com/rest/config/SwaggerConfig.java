/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rest.config;

import com.google.common.collect.Lists;
import static com.google.common.collect.Lists.newArrayList;
import com.rest.controller.RentalInfoController;
import com.rest.oauth2.config.AuthServerOAuth2Config;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 * @author Ataur Rahman
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.rest.controller"))
                //.apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(commonParameters())
                .apiInfo(apiInfo())
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()))
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, newArrayList(
                    new ResponseMessageBuilder()
                    .code(500)
                    .message("Internal Server Error")
                    .responseModel(new ModelRef("Error"))
                    .build(),
                    new ResponseMessageBuilder()
                    .code(200)
                    .message("Request successfully sent")
                    .responseModel(new ModelRef("Success"))
                    .build(),
                    new ResponseMessageBuilder()
                    .code(401)
                    .message("Unauthrized Access")
                    .responseModel(new ModelRef("Error"))
                    .build(),
                   new ResponseMessageBuilder() 
                    .code(403)
                    .message("Forbidden!")
                    .build()));
    }

    private ApiKey apiKey() {
        return new ApiKey("AUTHORIZATION", "access_token", "header");
    }

    private List<Parameter> commonParameters() {
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameters.add(new ParameterBuilder()
                .name("access_token")
                .description("token for authorization")
                .modelRef(new ModelRef("string"))
                .parameterType("query")
                .required(true)
                .build());

        return parameters;
    }

    @Bean
    SecurityConfiguration security() {
        return new SecurityConfiguration(
                null,
                null,
                AuthServerOAuth2Config.REALM + "/client", // realm Needed for authenticate button to work
                null, // appName Needed for authenticate button to work
                "access_token ",// apiKeyValue
                ApiKeyVehicle.HEADER,
                "access_token", //apiKeyName
                null);
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(
                new SecurityReference("AUTHORIZATION", authorizationScopes));
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "My REST API",
                "Some custom description of API.",
                "API TOS",
                "Terms of service",
                "myeaddress@company.com",
                "License of API",
                "API license URL");
        return apiInfo;
    }
}
