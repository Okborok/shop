package com.okborok.web.shop.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created on 28.10.2017.
 */

@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final static String AUTH_LOGIN_URL = "/auth/login";
    private final static int B_CRYPT_PASSWORD_ENCODER_STRENGTH = 10;

    //private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(B_CRYPT_PASSWORD_ENCODER_STRENGTH);
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http
                .headers().frameOptions().sameOrigin()

                .and()
                .authorizeRequests()
                .antMatchers("/**").permitAll()

                .and()
                .formLogin()
                .loginPage(AUTH_LOGIN_URL).permitAll()
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/")
                .failureUrl(AUTH_LOGIN_URL + "?error=true")

                .and()
                .logout().permitAll()
                .logoutSuccessUrl(AUTH_LOGIN_URL)

                .and()
                .csrf().disable()
                .exceptionHandling()
                .accessDeniedPage(AUTH_LOGIN_URL);

    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }
}