package com.bookingapp.config;

import com.bookingapp.security.auth.RestAuthenticationEntryPoint;
import com.bookingapp.security.auth.TokenAuthenticationFilter;
import com.bookingapp.services.CustomUserDetailsService;
import com.bookingapp.util.TokenUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.core.context.SecurityContextHolder.MODE_INHERITABLETHREADLOCAL;
import static org.springframework.security.core.context.SecurityContextHolder.setStrategyName;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;



@Log4j2
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        jsr250Enabled = true
)
public class WebSecurityConfig {

    public WebSecurityConfig() {
        // Inherit security context in async function calls
        setStrategyName(MODE_INHERITABLETHREADLOCAL);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private CustomUserDetailsService jwtUserDetailsService;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(jwtUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Autowired
    private TokenUtils tokenUtils;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http)
            throws Exception {

        http
                // Enable CORS
                .cors(withDefaults())
                //Disable CSRF
                .csrf(AbstractHttpConfigurer::disable)
                // Set session management to stateless
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(STATELESS))
                // Set unauthorized requests exception handler
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(restAuthenticationEntryPoint))
                // Set permission to allow open db-console
                .authorizeHttpRequests(auth ->{
                            auth.requestMatchers(antMatcher("/api/accommodations/create")).hasAuthority("OWNER");
                            ///api/amenities
                    //"/api/reviews"
                            auth.requestMatchers(antMatcher("/api/reviews")).hasAuthority("GUEST");
                            auth.requestMatchers(antMatcher("/api/amenities")).hasAuthority("OWNER");
                            auth.requestMatchers(antMatcher("/api/register/users")).permitAll(); ///api/users/login
                            auth.requestMatchers(antMatcher("/api/login")).permitAll();
                            auth.requestMatchers(antMatcher("/api/verify/users/{userId}")).permitAll();
                            auth.requestMatchers(antMatcher("/api/accommodations/get")).permitAll();
                            auth.requestMatchers(antMatcher("/api/accommodations/{id}")).permitAll();
                            auth.requestMatchers(antMatcher("/api/accommodations/filter")).permitAll();
                            auth.requestMatchers(antMatcher("/api/accommodations/search")).permitAll();
                            auth.requestMatchers(antMatcher("/api/accommodations/sort/rating/desc")).permitAll();
                            auth.requestMatchers(antMatcher("/api/accommodations/sort/rating/asc")).permitAll();
                            auth.requestMatchers(antMatcher("/api/accommodations/sort/price/desc")).permitAll();
                            auth.requestMatchers(antMatcher("/api/accommodations/sort/price/asc")).permitAll();
                            auth.requestMatchers(antMatcher("/api/requests")).permitAll();
                            auth.requestMatchers(antMatcher("/h2/**")).permitAll();
                            auth.requestMatchers(antMatcher("/api/users/token/{token}")).permitAll();
                            auth.anyRequest().authenticated();
                        }
                )
                .headers(headers ->
                        headers.frameOptions(FrameOptionsConfig::sameOrigin).disable())

                /*.authorizeHttpRequests(auth ->
                        // Upload files and manage countries
                        auth.requestMatchers("/upload/**", "/countries/**")
                                .hasAuthority(Role.ADMIN.name())
                                // Create cities
                                .requestMatchers(POST, "/cities")
                                .hasAuthority(Role.ADMIN)
                                .requestMatchers(POST,"/auth/change_password", "/auth/signout")
                                .hasAnyAuthority(Role.ADMIN, Role.CLIENT))
                */

                // Add JWT token filter
                .addFilterBefore(new TokenAuthenticationFilter(tokenUtils,  userDetailsService()), BasicAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers
                (antMatcher(HttpMethod.POST, "/api/login"),antMatcher(HttpMethod.POST, "/api/register/users"),antMatcher(HttpMethod.PUT, "/api/verify/users/{userId}"), antMatcher("/h2/**"),
                        antMatcher(HttpMethod.GET, "/api/accommodations/get"), antMatcher(HttpMethod.GET, "/api/accommodations/{id}"),
                        antMatcher(HttpMethod.GET, "/api/accommodations/search"), antMatcher(HttpMethod.GET, "/api/accommodations/filter"),
                        antMatcher(HttpMethod.GET, "/api/accommodations/sort/rating/desc"),
                        antMatcher(HttpMethod.GET, "/api/accommodations/sort/rating/asc"),
                        antMatcher(HttpMethod.GET, "/api/accommodations/sort/price/desc"),
                        antMatcher(HttpMethod.GET, "/api/accommodations/sort/price/asc"),
                        antMatcher(HttpMethod.POST, "/api/requests"),
                        antMatcher(HttpMethod.GET, "/api/users/token/{token}"));
    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:4200","http://192.168.1.4").allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("*");;
            }
        };
    }
}


