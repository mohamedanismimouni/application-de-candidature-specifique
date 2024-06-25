//package com.talan.polaris.configurations;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
//
//import com.talan.polaris.components.CustomMethodSecurityExpressionHandler;
//
///**
// * MethodSecurityConfiguration.
// * 
// * @author Nader Debbabi
// * @since 1.0.0
// */
//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class MethodSecurityConfiguration
//        extends GlobalMethodSecurityConfiguration {
//
//    private final CustomMethodSecurityExpressionHandler expressionHandler;
//
//    @Autowired
//    public MethodSecurityConfiguration(CustomMethodSecurityExpressionHandler expressionHandler) {
//        super();
//        this.expressionHandler = expressionHandler;
//    }
//    
//    @Override
//    protected MethodSecurityExpressionHandler createExpressionHandler() {
//        return this.expressionHandler;
//    }
//
//}
