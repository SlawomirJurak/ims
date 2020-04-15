package pl.sgnit.ims;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import pl.sgnit.ims.converter.LocalDateConverter;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Configuration
@ComponentScan("pl.sgnit.ims")
@EnableJpaRepositories(basePackages = "pl.sgnit.ims.repository")
@EnableTransactionManagement
public class AppConfig {
    @Bean(name = "localResolver")
    public LocaleContextResolver getLocaleContextResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(new Locale("pl", "PL"));
        return sessionLocaleResolver;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:validationMessages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public Validator validator() {
        LocalValidatorFactoryBean lvfb = new LocalValidatorFactoryBean();
        lvfb.setValidationMessageSource(messageSource());
        return lvfb;
    }

    private Set<Converter> getConverters() {
        Set<Converter> converters = new HashSet<>();
        converters.add(new LocalDateConverter());
        return converters;
    }

    @Bean(name="conversionService")
    public ConversionService getConversionService() {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        bean.setConverters(getConverters());
        bean.afterPropertiesSet();
        return bean.getObject();
    }

}
