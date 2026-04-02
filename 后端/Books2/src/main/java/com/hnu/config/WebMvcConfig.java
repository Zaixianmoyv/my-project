package com.hnu.config;

/**
 * @Author hj
 * @Date 2023/11/29 14:47
 * @PackageName:com.hnu.config
 * @ClassName: WebMvcConfig
 * @Description: create a class
 * @Version 1.0
 */

import com.hnu.util.JacksonObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * webmvc核心配置类，等同：spring-mvc.xml
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    //自定义转换器
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        //设置对象转换器，底层使用Jackson 将Java对象转为json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //将上面的消息转换器对象追加到mvc框架的转换器集合中
        converters.add(0,messageConverter);
    }

    /**
     * 视图解析器配置
     * @param registry
     *//*
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/");
        viewResolver.setSuffix(".jsp");
        registry.viewResolver(viewResolver);
    }

    *//**
     * 放行静态资源
     * <mvc:default-servlet-handler/>
     * @param configurer
     *//*
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }*/



    /**
     * 文件上传的配置
     * @return
     */
    @Bean
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver cfg = new CommonsMultipartResolver();
        //设置默认内存缓存文件的最大值：2kb
        cfg.setMaxInMemorySize(2048);
        //设置允许上传的总文件大小:1G
        cfg.setMaxUploadSize(1024*1024*1024);
        //设置允许上传的单个文件最大值:100M
        cfg.setMaxUploadSizePerFile(1024*1024*100);
        return cfg;
    }



    /**
     * 拦截器配置
     * @param registry
     */
    /*@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**")
                                .excludePathPatterns("/user/login")
                                .excludePathPatterns("/user/regist");
    }*/

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/photo/**").addResourceLocations("file:d:/books/");
    }
}
