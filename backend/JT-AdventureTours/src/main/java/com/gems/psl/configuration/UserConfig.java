package com.gems.psl.configuration;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.gems.psl.filter.JwtAuthFilter;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class UserConfig {
	
	 @Autowired
	    private JwtAuthFilter authFilter;

    @Bean
    UserDetailsService userDetailsService() {

	        return new UserInfoUserDetailsService();
	    }


     
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		 httpSecurity.cors(withDefaults()).csrf((csrf) -> csrf.disable()).authorizeHttpRequests((authz) -> {
			 try {
                 authz.requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                 .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                         .requestMatchers(new AntPathRequestMatcher("/auth/login")).permitAll()
                         .requestMatchers(new AntPathRequestMatcher("/auth/register")).permitAll()
                         
                         .requestMatchers(new AntPathRequestMatcher("/batches/tours/{id}")).permitAll()
                         .requestMatchers(new AntPathRequestMatcher("/batches")).permitAll()
                         .requestMatchers(new AntPathRequestMatcher("/batches")).permitAll()
                         .requestMatchers(new AntPathRequestMatcher("/batches/book")).hasAuthority("CUSTOMER")
                         .requestMatchers(HttpMethod.DELETE,"/batches/{id}").denyAll()
                         .requestMatchers(HttpMethod.GET,"/tours").permitAll()
                         .requestMatchers(HttpMethod.POST,"/tours").hasAuthority("ADMIN")
                         .requestMatchers(HttpMethod.PUT,"/tours").hasAuthority("ADMIN")
                         .requestMatchers(HttpMethod.POST,"/batches/tours/{id}").hasAuthority("ADMIN")
                         .requestMatchers(HttpMethod.GET,"/tours/{id}/batches").permitAll()
                         .requestMatchers(HttpMethod.PUT,"/batches/tours/{id}").hasAuthority("ADMIN")
                         .requestMatchers(HttpMethod.GET,"/booking").hasAuthority("ADMIN")
                         .requestMatchers(new AntPathRequestMatcher("/user/showUser"))
                         .hasAuthority("ADMIN")
                         .requestMatchers(new AntPathRequestMatcher("/tours"))
                         .hasAuthority("ADMIN")
                         .anyRequest().authenticated().and()
                         .sessionManagement(management -> management
                                 .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                         .authenticationProvider(authenticationProvider())
                         .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
					
			 }catch(Exception e) {
				 e.printStackTrace();
			 }
		 }) ;
		 return httpSecurity.build();
		 
		
	 }



     
    @Bean
    PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

    @Bean
    AuthenticationProvider authenticationProvider(){
	        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
	        authenticationProvider.setUserDetailsService(userDetailsService());
	        authenticationProvider.setPasswordEncoder(passwordEncoder());
	        return authenticationProvider;
	    }
	 
	 @Bean
	     AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	        return config.getAuthenticationManager();
	    }
	 
	 private SecurityScheme createAPIKeyScheme() {
	        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
	            .bearerFormat("JWT")
	            .scheme("bearer");
	    }
	    @Bean
	     OpenAPI openAPI() {
	        return new OpenAPI().addSecurityItem(new SecurityRequirement().
	                addList("Bearer Authentication"))
	            .components(new Components().addSecuritySchemes
	                ("Bearer Authentication", createAPIKeyScheme()))
	            .info(new Info().title("Assessment Platform")
	                .description("Some custom description of API.")
	                .version("1.0").contact(new Contact().name("Group_04")
	                    .email( "Group_04@gmail.com").url("Group_04@gmail.com"))
	                .license(new License().name("License of API")
	                    .url("API license URL")));
	    }
	 
}
