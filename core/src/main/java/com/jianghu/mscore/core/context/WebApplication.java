package com.jianghu.mscore.core.context;

import com.jianghu.mscore.core.mvc.web.controller.AppErrorController;
import com.jianghu.mscore.core.mvc.web.controller.AppExceptionHandlerController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Bean;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link com.jianghu.mscore.core.annotation.MspApplication}中加入ioc容器
 * swagger定义, 并将{@link AppErrorController}
 * 和{@link AppExceptionHandlerController}
 * 两个bean加入ioc容器做统一异常处理的定义
 *
 * @author hujiang.
 * @version 1.0
 * @since 2019.04.26
 */
@EnableSwagger2
public class WebApplication {

    /**
     * error page
     *
     * @param errorAttributes  the error attributes
     * @param serverProperties the server properties
     * @return the error controller
     * @since 2019.04.26
     */
    @Bean
    public ErrorController errorController(ErrorAttributes errorAttributes, ServerProperties serverProperties) {

        return new AppErrorController(errorAttributes, serverProperties.getError());
    }


    /**
     * exception handler
     *
     * @return the app exception handler controller
     * @since 2019.04.26
     */
    @Bean
    public AppExceptionHandlerController appExceptionHandlerController() {

        return new AppExceptionHandlerController();
    }


    /**
     * swagger2
     *
     * @return the docket
     * @since 2019.04.26
     */
    @Bean
    public Docket api() {
        List<Parameter> parameters = new ArrayList<>();
        ParameterBuilder authTicket = new ParameterBuilder();
        authTicket.name("cs-ssid").description("身份认证")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        parameters.add(authTicket.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .directModelSubstitute(LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(LocalDateTime.class, java.util.Date.class)
                .select()
                .apis(requestHandler -> {
                    assert requestHandler != null;
                    String packageName = requestHandler.getHandlerMethod().getMethod()
                            .getDeclaringClass().getPackage().getName();
                    return packageName.contains("controller");
                })
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("YouZhi MS 基于Swagger2构建Restful APIs Document")
                .description("优智微服务平台接口文档")
                .termsOfServiceUrl("https://www.qmwhd.com/")
                .version("1.0").build();
    }


}

