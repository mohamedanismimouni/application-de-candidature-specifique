//package com.talan.polaris.configurations;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.MessageSource;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.access.channel.ChannelProcessingFilter;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import com.talan.polaris.components.CORSFilter;
//import com.talan.polaris.components.XAuthTokenAuthenticationFilter;
//import com.talan.polaris.components.XAuthTokenAuthenticationProvider;
//
//import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_AUTHENTICATION_PREFIX;
//import static com.talan.polaris.constants.ConfigurationConstants.API_ENDPOINTS_RESOURCES_PREFIX;
//import static com.talan.polaris.constants.ConfigurationConstants.MANAGEMENT_ENDPOINTS_PREFIX;
//import static com.talan.polaris.enumerations.SessionTypeEnum.SessionTypeConstants.ACCOUNT_ACTIVATION_SESSION_TYPE;
//import static com.talan.polaris.enumerations.SessionTypeEnum.SessionTypeConstants.PASSWORD_RESET_SESSION_TYPE;
//import static com.talan.polaris.enumerations.SessionTypeEnum.SessionTypeConstants.ACCESS_SESSION_TYPE;
//import static com.talan.polaris.constants.MessagesConstants.ERROR_UNAUTHORIZED;
//
///**
// * SecurityConfiguration.
// * 
// * @author Nader Debbabi
// * @since 1.0.0
// */
//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    private final XAuthTokenAuthenticationProvider xAuthTokenAuthenticationProvider;
//    private final XAuthTokenAuthenticationFilter xAuthTokenAuthenticationFilter;
//    private final CORSFilter corsFilter;
//    private final MessageSource messageSource;
//    private final String apiEndpointsAuthenticationPrefix;
//    private final String apiEndpointsResourcesPrefix;
//    private final String managementEndpointsPrefix;
//
//    @Autowired
//    public SecurityConfiguration(
//            XAuthTokenAuthenticationProvider xAuthTokenAuthenticationProvider,
//            XAuthTokenAuthenticationFilter xAuthTokenAuthenticationFilter,
//            CORSFilter corsFilter,
//            MessageSource messageSource,
//            @Value("${" + API_ENDPOINTS_AUTHENTICATION_PREFIX + "}")
//            String apiEndpointsAuthenticationPrefix,
//            @Value("${" + API_ENDPOINTS_RESOURCES_PREFIX + "}")
//            String apiEndpointsResourcesPrefix,
//            @Value("${" + MANAGEMENT_ENDPOINTS_PREFIX + "}")
//            String managementEndpointsPrefix) {
//
//        this.xAuthTokenAuthenticationProvider = xAuthTokenAuthenticationProvider;
//        this.xAuthTokenAuthenticationFilter = xAuthTokenAuthenticationFilter;
//        this.corsFilter = corsFilter;
//        this.messageSource = messageSource;
//        this.apiEndpointsAuthenticationPrefix = apiEndpointsAuthenticationPrefix;
//        this.apiEndpointsResourcesPrefix = apiEndpointsResourcesPrefix;
//        this.managementEndpointsPrefix = managementEndpointsPrefix;
//
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(this.xAuthTokenAuthenticationProvider);
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//
//        http.sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        http.authorizeRequests()
//            .antMatchers(HttpMethod.OPTIONS).permitAll()
//            .antMatchers(HttpMethod.POST, this.apiEndpointsAuthenticationPrefix + "/sign-in").permitAll()
//                .antMatchers(HttpMethod.POST, this.apiEndpointsAuthenticationPrefix + "/sign-up").permitAll()
//                .antMatchers(HttpMethod.POST, this.apiEndpointsResourcesPrefix + "/users/password-reset-mail").permitAll()
//            .antMatchers(this.managementEndpointsPrefix + "/**").permitAll()
//            .antMatchers(HttpMethod.POST, this.apiEndpointsResourcesPrefix + "/users/account-activation")
//                .access("authenticated and @sessionTypeBasedAccessDecider.check(authentication,'" + ACCOUNT_ACTIVATION_SESSION_TYPE + "')")
//            .antMatchers(HttpMethod.POST, this.apiEndpointsResourcesPrefix + "/users/password-reset")
//                .access("authenticated and @sessionTypeBasedAccessDecider.check(authentication,'" + PASSWORD_RESET_SESSION_TYPE + "')")
//            .anyRequest()
//                .access("authenticated and @sessionTypeBasedAccessDecider.check(authentication,'" + ACCESS_SESSION_TYPE + "')");
//
//        http.csrf().disable();
//
//        http.logout().disable();
//
//        http.requestCache().disable();
//
//        http.addFilterBefore(this.corsFilter, ChannelProcessingFilter.class);
//        http.addFilterBefore(this.xAuthTokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//        http.exceptionHandling()
//            .authenticationEntryPoint(
//                (req, rsp, e) -> rsp.sendError(
//                    HttpServletResponse.SC_UNAUTHORIZED,
//                    this.messageSource.getMessage(ERROR_UNAUTHORIZED, null, req.getLocale())
//                )
//            );
//
//    }
//
//}
