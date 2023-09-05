package com.ecommerce.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfiguration {
		
		@Lazy
		@Autowired
	   private UserDetailsService jwtService;

		@Autowired
	   private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

		@Autowired
	   private JwtRequestFilter jwtRequestFilter;

	   @Bean
	   public PasswordEncoder passwordEncoder() {
	       return new BCryptPasswordEncoder();
	   }

	   @Bean
	   public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	       return authenticationConfiguration.getAuthenticationManager();
	   }

	   @Bean
	   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	       http.cors();
	       http.csrf().disable()
	       	.authorizeHttpRequests().requestMatchers("/authenticate", "/registerNewUser", "/getAllProducts", "/getProductDetailsById/{productId}").permitAll()
	       	.requestMatchers(HttpHeaders.ALLOW).permitAll()
	       	.anyRequest().authenticated()
	       	.and()
	       	.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
	       	.and()
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	       http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	       return http.build();
	   }
	   //just for set github again
	   
//	   @Autowired
//	    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//	        authenticationManagerBuilder.userDetailsService(jwtService).passwordEncoder(passwordEncoder());
//	    }

//	   @Bean
//	   public WebMvcConfigurer corsConfigurer() {
//	       return new WebMvcConfigurer() {
//	           @Override
//	           public void addCorsMappings(CorsRegistry registry) {
//	               registry.addMapping("/**")
//	                       .allowedMethods("*");
//	           }
//	       };
//	   }
	}