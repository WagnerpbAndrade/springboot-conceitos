package br.com.wagnerandrade.springboot.config;

import br.com.wagnerandrade.springboot.service.SpringUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.logging.Logger;

@EnableWebSecurity
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private Logger logger = Logger.getLogger(SecurityConfig.class.getName());

    private final SpringUserDetailsService springUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .authorizeRequests()
                .antMatchers("/api/v1/animes/admin/**").hasRole("ADMIN")
                .antMatchers("/api/v1/animes/**").hasRole("USER")
                .antMatchers("/actuator/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        logger.info("Password encoded {}" + passwordEncoder.encode("teste"));

        auth.inMemoryAuthentication()
                .withUser("wagner2")
                .password(passwordEncoder.encode("123"))
                .roles("USER", "ADMIN")
                .and()
                .withUser("teste")
                .password(passwordEncoder.encode("123"))
                .roles("USER");

        auth.userDetailsService(springUserDetailsService)
                .passwordEncoder(passwordEncoder);
    }
}
