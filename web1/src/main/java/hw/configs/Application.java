package hw.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * Created by zotova on 06.07.2016.
 */

@Configuration
@EnableWebMvc
@ComponentScan({"hw"})
@EnableJms
public class Application  extends WebMvcConfigurerAdapter { // extends SpringBootServletInitializer  {

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver
                = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/views/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}





