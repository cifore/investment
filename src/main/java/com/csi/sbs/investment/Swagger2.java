package com.csi.sbs.investment;
 
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.csi.sbs.investment.business.constant.SysConstant;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
 
/**
 * Swagger2配置类
 * 在与spring boot集成时，放在与Application.java同级的目录下。
 * 通过@Configuration注解，让Spring来加载该类配置。
 * 再通过@EnableSwagger2注解来启用Swagger2。
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
    
    /**
     * 创建API应用
     * apiInfo() 增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
     * 本例采用指定扫描的包路径来定义指定要建立API的目录。
     * 
     * @return
     */
    @Bean
    public Docket createRestApi() {
    	ParameterBuilder parameterBuilder = new ParameterBuilder();
    	List<Parameter> parameters = new ArrayList<Parameter>();
        parameterBuilder.name("token")
        .modelRef(new ModelRef("string"))
        .parameterType("header")
        .description("The most recent customer authorization token to third party app to access customer data from a bank, in JWT format.")
        .defaultValue("eyJhbGciOiJIUzUxMiIsInppcCI6IkRFRiJ9.eNo8y0sKwjAUheG9ZOzg5rZptFMdKEL3kMe1BpoHsRFF3LsJitPvnP_FLN1piYny6cBGJixttVTmIhB7RLMji1r2oDU3XErBNszEEtb83EdLNTieGy2ksgvzzwCAV9VZBXP9W6Mlzi5MyjdJzre03NboKU_Fa8rfI3QoYagjPRIbuRADdAIB3h8AAAD__w.UbGDRxKA72AOiTfd04Eje34WuoX22jfDGoAtKlgKpWglb26RFucDnq5MCdJArKN_NxIG9l3Ekj4jU6_j2Fw_ew")
        .required(true).build();
        parameters.add(parameterBuilder.build());
        
        parameterBuilder.name("messageid")
        .modelRef(new ModelRef("string"))
        .parameterType("header")
        .description("Message ID in 128 bit random UUID format generated uniquely for every request by F/E side.")
        .defaultValue("006f7113e5fa48559549c4dfe74e2cd6")
        .required(true).build();
        parameters.add(parameterBuilder.build());
        
        parameterBuilder.name("clientid")
        .modelRef(new ModelRef("string"))
        .parameterType("header")
        .description("client_id generated during consumer onboarding. ")
        .defaultValue("devin")
        .required(true).build();
        parameters.add(parameterBuilder.build());
        
        
        return new Docket(DocumentationType.SWAGGER_2)
        		.globalOperationParameters(parameters)
                .host(SysConstant.GATEWAY_SERVICE)
                .groupName("investment-api")
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.csi.sbs.investment.business.controller"))
                .paths(PathSelectors.any())
                .build()
                .directModelSubstitute(java.sql.Timestamp.class, java.sql.Date.class)
                .enableUrlTemplating(false);
    }
    
    /**
     * 创建该API的基本信息（这些基本信息会展现在文档页面中）
     * 访问地址：http://项目实际地址/swagger-ui.html
     * @return
     */
    @SuppressWarnings("deprecation")
	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Open API for investment service")
                .description(SysConstant.LOCAL_DESCRIBE)
                .termsOfServiceUrl(SysConstant.LOCAL_SERVICE_URL)
                .contact("Pim li:lihuacheng@chinasofti.com")
                .version("1.0")
                .build();
    }
}
