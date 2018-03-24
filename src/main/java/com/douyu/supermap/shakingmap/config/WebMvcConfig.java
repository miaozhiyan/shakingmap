package com.douyu.supermap.shakingmap.config;

import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

//public class WebMvcConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {
//    private ApplicationContext applicationContext;

//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//    }

    /**
     * 模板资源解析器
     * @return
     */
//    @Bean
//    @ConfigurationProperties(prefix = "spring.thymeleaf")
//    public SpringResourceTemplateResolver templateResolver(){
//        return  new SpringResourceTemplateResolver();
//    }

    /**
     * Thymeleaf标准方言解析器
     * @return
     */
//    @Bean
//    public SpringResourceTemplateResolver templateEngine(){
//        SpringResourceTemplateResolver templateEngine = new SpringResourceTemplateResolver();
//        templateEngine.setApplicationContext(applicationContext);
//        //支持Spring EL表达式
//        return templateEngine;
//    }

    /**
     * 视图解析器
     */
//    @Bean
//    public ThymeleafViewResolver viewResolver(){
//        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
//        viewResolver.setCache(false);
//        viewResolver.setTemplateEngine( (ISpringTemplateEngine) templateEngine());
//        viewResolver.setOrder(1);
//        return viewResolver;
//    }
//}
