package org.quanta.core.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.RequiredArgsConstructor;
import org.quanta.base.config.SystemConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Description:
 * Author: wzf
 * Date: 2023/10/6
 */
@EnableSwagger2
@EnableKnife4j
@EnableOpenApi
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig extends DelegatingWebMvcConfiguration {
    private final SystemConfig systemConfig;

    @Bean
    public Docket createRestApis() {
        return new Docket(DocumentationType.OAS_30)
                .enable(true) // 是否启用：注意生产环境需要关闭
                .groupName("quanta")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .ignoredParameterTypes(CookieValue.class)
                .apiInfo(apiInfo())
                .select()
                // 以下拦截配置可以三选一，根据需要进行添加
                .apis(RequestHandlerSelectors.basePackage("org.quanta"))
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                // 设置basePath
                .pathMapping("/" + systemConfig.getAppName());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("接口文档")
                .description("")
                // 服务条款URL
                .termsOfServiceUrl("https://quantacenter.com/")
                // 作者信息
                .contact(new Contact("quanta", "https://quantacenter.com/", "quantacenter@163.com"))
                .version("0.0.1")
                .build();
    }

}
