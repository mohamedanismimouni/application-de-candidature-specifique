package com.talan.polaris.components;
import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import static com.talan.polaris.constants.ConfigurationConstants.SECURITY_CORS_ALLOWED_ORIGINS;

/**
 * CORSFilter.
 *
 * @author  Nader Debbabi
 * @since   1.0.0
 */
@Component
public class CORSFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CORSFilter.class);

    @Value("#{'${" + SECURITY_CORS_ALLOWED_ORIGINS + "}'.split(',')}")
    private List<String> allowedOrigins;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String origin = request.getHeader("Origin");

        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Request from: " + origin + " is being proccessed by CORSFilter");

        response.setHeader("Access-Control-Allow-Origin", this.allowedOrigins.contains(origin) ? origin : "");
        response.setHeader("Vary", "Origin");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, PATCH");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, X-Auth-Token, X-Forwarded-For");

        filterChain.doFilter(request, response);

    }
 

}
