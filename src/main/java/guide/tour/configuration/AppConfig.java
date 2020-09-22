package guide.tour.configuration;

import com.maxmind.geoip2.DatabaseReader;
import guide.tour.converter.RoleToUserProfileConverter;
import guide.tour.security.ActiveUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.format.FormatterRegistry;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import ua_parser.Parser;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

/**
 * AppConfig configuration class.
 *
 * @author Anna Likhachova
 * @version 1.0
 */

@Configuration
@ComponentScan(basePackages = "guide.tour.*")
@PropertySource(value = { "classpath:application.properties" })
@EnableWebMvc
@EnableJpaRepositories("guide.tour.dao")
public class AppConfig implements WebMvcConfigurer{
	
private static final Logger log = LoggerFactory.getLogger(AppConfig.class);
	
	@Autowired
    RoleToUserProfileConverter roleToUserProfileConverter;

    @Autowired
    private Environment env;

	@Override
    public void addInterceptors(final InterceptorRegistry registry) {
        final LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor);
    }
	
	@Override
	public org.springframework.validation.Validator getValidator() {
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		validator.setValidationMessageSource(messageSource());
		return validator;
	}

    /**
     * Configure ViewResolvers to deliver preferred views.
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
 
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        registry.viewResolver(viewResolver);
    }
    
    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
    		registry.addViewController("/").setViewName("index");
        registry.addViewController("/login");
        registry.addViewController("/invalidSession");
        registry.addViewController("/error");
        registry.addViewController("/registrationConfirm");
        registry.addViewController("/registration");
        registry.addViewController("/invalidUser");
        registry.addViewController("/forgetPassword");
        registry.addViewController("/changePassword");
        registry.addViewController("/welcome");
        registry.addViewController("/successRegister");
        registry.addViewController("/information");
        registry.addViewController("/logout");
    }
     
    /**
     * Configure ResourceHandlers to serve static resources CSS, Javascript
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }
     
    /**
     * Configure Converter to be used.
     * In our example, we need a converter to convert string values to UserProfiles.
    */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(roleToUserProfileConverter);
    }

    /**
     * Configure MessageSource to lookup any validation/error message in internationalized property files
     */
    @Bean
    public MessageSource messageSource() {
    	ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("/WEB-INF/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
    
    @Bean
    public ActiveUser activeUser() {
        return new ActiveUser();
    }
    
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.ENGLISH);
        return slr;
    }

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver createMultipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("utf-8");
		return resolver;
	}

	@Bean
	public RestTemplate restTemplate() {
	    RestTemplate restTemplate = new RestTemplate();
	    return restTemplate;
	}

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(env.getProperty("spring.mail.username"));
        mailSender.setPassword(env.getProperty("spring.mail.password"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
	
	@Bean
    public Parser uaParser() throws IOException {
        return new Parser();
    }

    @Bean(name="GeoIPCity")
    public DatabaseReader databaseReader() throws IOException {
        File database = ResourceUtils
                .getFile("classpath:maxmind/GeoLite2-City.mmdb");
        return new DatabaseReader.Builder(database)
                .build();
    }

}
