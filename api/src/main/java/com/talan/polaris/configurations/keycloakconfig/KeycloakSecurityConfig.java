package com.talan.polaris.configurations.keycloakconfig;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
 import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_RESOURCES_PREFIX;
import static com.talan.polaris.constants.ConfigurationConstants.MANAGEMENT_ENDPOINTS_PREFIX;



import com.google.common.collect.ImmutableList;


@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true,
                        securedEnabled = true,
                        jsr250Enabled = true)
 
public class KeycloakSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {
	 @Value("${" + MANAGEMENT_ENDPOINTS_PREFIX + "}")
	 private String managementEndpointsPrefix;
	 @Value("${" + API_ENDPOINTS_RESOURCES_PREFIX + "}")
	 private String apiEndpointPrefix;
	
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        super.configure(http);
	        http.sessionManagement()
	        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	        http.authorizeRequests()
	        	.antMatchers(this.managementEndpointsPrefix + "/**").permitAll()
					.antMatchers("/**").permitAll()
	        	.anyRequest().authenticated();
	        http.csrf().disable();
	        http.cors();
	    }
	 @Bean
	    public CorsConfigurationSource corsConfigurationSource() {
	        final CorsConfiguration configuration = new CorsConfiguration();
	        configuration.setAllowedOrigins(ImmutableList.of("*"));
	        configuration.setAllowedMethods(ImmutableList.of("HEAD",
	                "GET", "POST", "PUT", "DELETE", "PATCH"));
	        // setAllowCredentials(true) is important, otherwise:
	        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
	        configuration.setAllowCredentials(true);
	        // setAllowedHeaders is important! Without it, OPTIONS preflight request
	        // will fail with 403 Invalid CORS request
	        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
	        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        return source;
	    }
	 

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

 
    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

    @Bean
    public KeycloakConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }
}