//package com.jianghu.mscore.core.config;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.PropertyNamingStrategy;
//import com.google.common.collect.Lists;
//import com.jianghu.mscore.core.mvc.web.interceptor.ApiAuthInterceptor;
//import com.jianghu.mscore.core.mvc.web.interceptor.DefaultAclInterceptor;
//import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.StringHttpMessageConverter;
//import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
//import org.springframework.web.servlet.config.annotation.*;
//
//import javax.annotation.Resource;
//import java.nio.charset.Charset;
//import java.text.SimpleDateFormat;
//import java.util.List;
//
///**
// * 全局配置
// *
// * @author hujiang.
// * @version 1.0
// * @since 2019.04.26
// */
//@Configuration
//public class GlobalConfigurer extends WebMvcConfigurationSupport {
//
//    private final static Charset UTF8 = Charset.forName("UTF-8");
//
//    @Resource
//    private ApiAuthInterceptor apiAuthInterceptor;
//    @Resource
//    private DefaultAclInterceptor defaultAclInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(defaultAclInterceptor).addPathPatterns("/**");
//        registry.addInterceptor(apiAuthInterceptor).addPathPatterns("/api/**");
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }
//
//    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.clear();
//        converters.add(getStringConverter());
//        converters.add(getJsonConverter());
//    }
//
//    /**
//     * Gets json.
//     * 定义json转换,修改默认编码
//     *
//     * @return the json
//     */
//    private HttpMessageConverter getJsonConverter() {
//        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
//        List<MediaType> mediaTypes = Lists.newArrayList();
//        mediaTypes.add(new MediaType("application", "json", UTF8));
//        mediaTypes.add(new MediaType("application", "*+json", UTF8));
//        messageConverter.setSupportedMediaTypes(mediaTypes);
//        messageConverter.setObjectMapper(objectMapper());
//        return messageConverter;
//    }
//
//    /**
//     * Gets string converter.
//     * 自定义字符串转换 修改默认编码
//     *
//     * @return the string converter
//     */
//    private HttpMessageConverter getStringConverter() {
//        StringHttpMessageConverter messageConverter = new StringHttpMessageConverter();
//        List<MediaType> mediaTypes = Lists.newArrayList();
//        mediaTypes.add(new MediaType(MediaType.TEXT_PLAIN, UTF8));
//        messageConverter.setSupportedMediaTypes(mediaTypes);
//        return messageConverter;
//    }
//
//
//    /**
//     * Gets string ObjectMapper.
//     * 日期类型处理objectMapper
//     *
//     * @return ObjectMapper
//     */
//    private ObjectMapper objectMapper() {
//        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
//        builder.propertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
//        builder.serializationInclusion(JsonInclude.Include.ALWAYS);
//        builder.failOnEmptyBeans(false);
//        builder.failOnUnknownProperties(false);
//        builder.indentOutput(true).dateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
//        return builder.build();
//    }
//
//    /**
//     * zuul已添加跨域处理，该方法中针对特定url开启跨域配置，全局配置与zuul冲突
//     * @param corsRegistry
//     */
//    public void addCorsMappings(CorsRegistry corsRegistry) {
////        corsRegistry.addMapping("/visitor/save/**")
////                .allowedOrigins("*")
////                .allowedMethods("*")
////                .allowedHeaders("*")
////                .allowCredentials(true).maxAge(3600);
//    }
//}
//
