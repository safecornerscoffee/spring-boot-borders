package com.safecornerscoffee.borders.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Profile("mariadb")
@Configuration
@EnableWebSecurity
public class MariaDbJdbcWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    public MariaDbJdbcWebSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/orders")
                    .access("hasRole('ROLE_USER')")
                .antMatchers("/", "/**")
                    .access("permitAll")
                .and()
                .formLogin()
                    .loginPage("/signin")
                    .usernameParameter("email")
                    .passwordParameter("password")
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                .and()
                    .csrf();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery("select email, password, enabled from members " + "where email=?")
            .authoritiesByUsernameQuery("select email, authority from authorities " + "where email=?")
            .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
