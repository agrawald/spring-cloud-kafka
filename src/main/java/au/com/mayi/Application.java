package au.com.mayi;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableKafka
@SpringBootApplication
public class Application implements InitializingBean {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// @Value("${/parameter_name}")
	// String value;

	@Configuration
	public static class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().anyRequest().permitAll().and().csrf().disable();
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// log.info("From parameter store: {}", value);

	}
}
