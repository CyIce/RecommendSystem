package com.bookrecommend.demo.config;

import com.bookrecommend.demo.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //访问http://localhost:8080/ 和 http://localhost:8080/index.html都会寻找静态资源下的templates/login.html
        registry.addViewController("/").setViewName("login");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/index", "/", "/login", "/user/login", "/user/logout", "/book", "/library", "/error",
                        "/**/*.css", "/**/*.js", "/**/*.jpg", "/**/*.png", "/**/*.map", "/**/*.ttf");
    }
}
