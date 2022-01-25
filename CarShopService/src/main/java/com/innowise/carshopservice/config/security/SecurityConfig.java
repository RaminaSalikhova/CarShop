package com.innowise.carshopservice.config.security;

import com.innowise.carshopservice.security.jwt.AuthorizationFilter;
import com.innowise.carshopservice.security.jwt.JwtAuthenticationEntryPoint;
import com.innowise.carshopservice.security.jwt.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private AuthorizationFilter authorizationFilter;

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/carshop/users/login", "/carshop/users/register", "/swagger*/**","/v2/api-docs", "/configuration/**", "/webjars/**").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/carshop/advertisements/").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/carshop/users/{^[\\\\d]$}").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/carshop/advertisements/{^[\\\\d]$}/contacts/").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/carshop/cars/{^[\\\\d]$}/photos/").permitAll()
                .and().authorizeRequests().antMatchers(HttpMethod.GET, "/carshop/photos/{[a-zA-Z]*[0-9]*.(jpg|png|gif|bmp)}/").permitAll()
                .anyRequest().authenticated().and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("*");
    }
}
