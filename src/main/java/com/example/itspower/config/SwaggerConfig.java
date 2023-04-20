package com.example.itspower.config;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

//@Configuration
//@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {
//    private ApiKey apiKey(){
//        return new ApiKey("JWT", "Authorization", "header");
//    }
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .securityContexts(Arrays.asList(securityContext()))
//                .securitySchemes(Arrays.asList(apiKey()))
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build();
//    }
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }
//    private ApiInfo apiInfo() {
//        Contact contact = new Contact("POWER-MOR", "http://www.power2sme.com", "support@power2sme.com");
//        return new ApiInfo("POWER API", "Information related to api exposed by POWER system.", "1.0",
//                "https://www.power2sme.com/termsandconditions", contact, "License of API",
//                "https://www.power2sme.com/privacypolicy", new ArrayList<>());
//    }
//    private SecurityContext securityContext(){
//        return SecurityContext.builder().securityReferences(defaultAuth()).build();
//    }
//    private List<SecurityReference> defaultAuth(){
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
//    }
}
