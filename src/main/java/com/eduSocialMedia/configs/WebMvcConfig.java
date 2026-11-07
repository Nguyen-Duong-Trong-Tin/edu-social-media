package com.eduSocialMedia.configs;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.eduSocialMedia.annotations.AuthInterceptor;
import com.eduSocialMedia.annotations.CurrentUserArgumentResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
  private final AuthInterceptor authInterceptor;
  private final CurrentUserArgumentResolver currentUserArgumentResolver;

  WebMvcConfig(AuthInterceptor authInterceptor, CurrentUserArgumentResolver currentUserArgumentResolver) {
    this.authInterceptor = authInterceptor;
    this.currentUserArgumentResolver = currentUserArgumentResolver;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(this.authInterceptor).addPathPatterns("/v1/**");
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(this.currentUserArgumentResolver);
  }
}
