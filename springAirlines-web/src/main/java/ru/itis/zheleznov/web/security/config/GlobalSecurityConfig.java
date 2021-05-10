package ru.itis.zheleznov.web.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.itis.zheleznov.web.security.details.UserDetailsServiceImpl;
import ru.itis.zheleznov.web.security.filters.GoogleFilter;
import ru.itis.zheleznov.web.security.filters.UserFilter;
import ru.itis.zheleznov.web.security.jwt.AuthTokenFilter;
import ru.itis.zheleznov.web.security.jwt.TokenAuthenticationFilter;
import ru.itis.zheleznov.web.security.jwt.TokenAuthenticationProvider;

import javax.servlet.Filter;

@EnableWebSecurity
public class GlobalSecurityConfig {

    @Order(2)
    @Configuration
    public static class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        private UserFilter userFilter;

        @Autowired
        private GoogleFilter googleUserFilter;

        @Autowired
        @Qualifier("UserDetailsServiceImpl")
        private UserDetailsService userDetailsService;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                        .antMatchers("/profile", "/booking/**").authenticated().and()
                    .formLogin()
                        .loginPage("/signIn")
                        .usernameParameter("email")
                        .defaultSuccessUrl("/profile")
                        .failureUrl("/signIn?error").and()
                    .oauth2Login().
                        defaultSuccessUrl("/profile").and()
                    .addFilterAfter(userFilter, UsernamePasswordAuthenticationFilter.class)
                    .addFilterAfter(googleUserFilter, UsernamePasswordAuthenticationFilter.class)
                    .logout()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                        .logoutSuccessUrl("/signIn")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID");
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        }
    }

    @Order(1)
    @Configuration
    public static class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Autowired
        @Qualifier("UserDetailsServiceImpl")
        UserDetailsServiceImpl userDetailsService;

        @Autowired
        private TokenAuthenticationFilter tokenAuthenticationFilter;

        @Autowired
        private TokenAuthenticationProvider tokenAuthenticationProvider;

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

        @Bean
        public AuthTokenFilter authenticationJwtTokenFilter() {
            return new AuthTokenFilter();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/api/**")
                        .addFilterAt(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                        .csrf().disable()
                        .sessionManagement().disable()
                        .authorizeRequests()
                            .antMatchers("/api/auth/**", "/api/cities").permitAll()
                            .anyRequest().authenticated().and();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(tokenAuthenticationProvider);
        }
    }
}
