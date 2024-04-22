package com.iting.common.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfiguration implements WebMvcConfigurer {

	@Value("${file-upload-folder}")
	String filePath;
	
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
	
        registry.addResourceHandler("/download/**")
        //.addResourceLocations(filePath)
        .addResourceLocations("file:///" + filePath)
        
        // 접근 파일 캐싱 시간 
        .setCacheControl(CacheControl.maxAge(1, TimeUnit.MINUTES));
        
        System.out.println(filePath + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}