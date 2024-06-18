package com.tss.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    @Qualifier("dataSourceAuth")
    private DataSource dataSourceAuth;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSourceAuth)
                .usersByUsernameQuery(
                        "select user_name, user_pass_crypt, enabled from users where user_name=?")
                .authoritiesByUsernameQuery(
                        "select user_name, role_name from user_roles where user_name=?");
    }

    @Bean
    SecurityFilterChain filterchain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .requiresChannel()
                .anyRequest()
                .requiresSecure();

        return httpSecurity.build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder(12);
        return encoder;
    }
}