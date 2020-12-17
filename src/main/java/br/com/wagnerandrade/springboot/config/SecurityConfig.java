package br.com.wagnerandrade.springboot.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.logging.Logger;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private Logger logger = Logger.getLogger(SecurityConfig.class.getName());

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        logger.info("Password encoded {}" + passwordEncoder.encode("test"));
        auth.inMemoryAuthentication()
                .withUser("wagner")
                .password(passwordEncoder.encode("123"))
                .roles("USER", "ADMIN")
                .and()
                .withUser("teste")
                .password(passwordEncoder.encode("123"))
                .roles("USER");
    }
}
