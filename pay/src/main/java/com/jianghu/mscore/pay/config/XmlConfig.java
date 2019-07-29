package com.jianghu.mscore.pay.config;

import com.google.common.collect.Lists;
import com.jianghu.mscore.context.config.GlobalConfigurer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.List;

@Configuration
public class XmlConfig extends GlobalConfigurer {


    private final static Charset UTF8 = Charset.forName("UTF-8");

    /**
     * Gets xml converter.
     * 自定义字符串转换 修改默认编码
     *
     * @return the xml converter
     */
    private HttpMessageConverter getXmlConverter() {
        MappingJackson2XmlHttpMessageConverter xmlConverter = new MappingJackson2XmlHttpMessageConverter();
        List<MediaType> mediaTypes = Lists.newArrayList();
        mediaTypes.add(new MediaType(MediaType.TEXT_XML, UTF8));
        xmlConverter.setSupportedMediaTypes(mediaTypes);
        return xmlConverter;
    }

    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.extendMessageConverters(converters);
        converters.add(getXmlConverter());
    }
}
