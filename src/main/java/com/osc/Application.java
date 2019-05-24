package com.osc;

import java.util.Optional;

import com.osc.config.AppProperties;
import com.osc.config.EmailProperties;
import com.osc.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;
import com.osc.security.AppUserDetails;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class,
		AppProperties.class,
		EmailProperties.class
})
public class Application extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application .class);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	//App Config
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}

@Configuration
@EnableJpaAuditing
class DataJpaConfig {

    @Bean
    public AuditorAware<AppUserDetails> auditor() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .filter(Authentication::isAuthenticated)
            .map(Authentication::getPrincipal)
            .map(AppUserDetails.class::cast);
    }
}
