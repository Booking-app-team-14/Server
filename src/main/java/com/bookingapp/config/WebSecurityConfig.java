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
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
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
                //.cors(withDefaults())
                //Disable CSRF
                .csrf(AbstractHttpConfigurer::disable)
                // Set session management to stateless
                .sessionManagement(customizer -> customizer.sessionCreationPolicy(STATELESS))
                // Set unauthorized requests exception handler
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(restAuthenticationEntryPoint))
                // Set permission to allow open db-console
                .authorizeHttpRequests(auth ->{
                    ///api/reviewReports/accommodationReviews/report
                            auth.requestMatchers(antMatcher("/api/accommodations/create")).hasAuthority("OWNER");

                    auth.requestMatchers(antMatcher("/api/userReports/isReported/{userId}")).hasAnyAuthority ("OWNER", "GUEST");
                            //    api/ownerReviewReports/reviews/report
                    auth.requestMatchers(antMatcher("/api/notifications/{notificationId}")).permitAll();
                    auth.requestMatchers(antMatcher("/api/ownerReviewReports/reviews/report")).hasAuthority("OWNER");
                    auth.requestMatchers(antMatcher("/api/reviews/report/{reviewId}")).hasAuthority("OWNER");
                    auth.requestMatchers(antMatcher("/api/ownerReviewReports")).hasAuthority("ADMIN");
                    auth.requestMatchers(antMatcher("/api/ownerReviewReports/{id}")).hasAuthority("ADMIN");
                    auth.requestMatchers(antMatcher("/api/ownerReviewReports/ownerReviews/{reportId}")).hasAuthority("ADMIN");
                    auth.requestMatchers(antMatcher("/api/reviewReports/accommodationReviews/report")).hasAuthority("OWNER");
                            auth.requestMatchers(antMatcher("/api/accommodations/update/{accommodationId}")).hasAuthority("OWNER");
                            auth.requestMatchers(antMatcher("/api/reviews/{reviewId}")).hasAnyAuthority("ADMIN", "GUEST");
                            auth.requestMatchers(antMatcher("/api/reviews")).hasAuthority("GUEST");
                            auth.requestMatchers(antMatcher("/api/accommodations/update")).hasAuthority("OWNER");
                            auth.requestMatchers(antMatcher("/api/reviews/owner/requests")).hasAuthority("ADMIN");
                            auth.requestMatchers(antMatcher("/api/reviews/accommodation/requests")).hasAuthority("ADMIN");
                            auth.requestMatchers(antMatcher("/api/reviews/admin/accommodation/{accommodationId}")).hasAuthority("ADMIN");
                            auth.requestMatchers(antMatcher("/api/accommodations/{accommodationId}/rating")).permitAll();
                            auth.requestMatchers(antMatcher("/api/users/{id}/image-type-username")).permitAll();
                            auth.requestMatchers(antMatcher("/api/owners/{id}/rating")).permitAll();
                    auth.requestMatchers(antMatcher("/api/reviews/owner/{ownerId}")).permitAll();
                    auth.requestMatchers(antMatcher("/api/reviews")).hasAuthority("GUEST");
                    auth.requestMatchers(antMatcher("/api/reviews/admin/{reviewId}")).hasAuthority("ADMIN");
                            auth.requestMatchers(antMatcher("/api/amenities")).hasAuthority("OWNER");
                            auth.requestMatchers(antMatcher("/api/register/users")).permitAll(); ///api/users/login
                            auth.requestMatchers(antMatcher("/api/login")).permitAll();
                    auth.requestMatchers(antMatcher("/api/reviews/owner/{ownerId}/average-rating")).permitAll();
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
                            auth.requestMatchers(antMatcher("/api/requests/{id}")).permitAll();
                            auth.requestMatchers(antMatcher("/api/requests/guest/{id}")).permitAll();
                            auth.requestMatchers(antMatcher("/api/requests/owner/{username}")).permitAll();
                            auth.requestMatchers(antMatcher("/api/guest/{id}")).permitAll();
                            auth.requestMatchers(antMatcher("/api/users/{id}/favorite-accommodations/{accommodationId}")).permitAll();
                    auth.requestMatchers(antMatcher("/api/userReports/report")).hasAnyAuthority("GUEST","OWNER"  );
                    //auth.requestMatchers(antMatcher("/api/userReports/report")).hasAuthority("OWNER" );
                    auth.requestMatchers(antMatcher("/api/accommodations/accommodationReviews")).hasAuthority("GUEST");
                    auth.requestMatchers(antMatcher("/api/accommodations/accommodations-mobile/sort/price/asc")).permitAll();
                            auth.requestMatchers(antMatcher("/api/role")).permitAll();
                            auth.requestMatchers(antMatcher("/api/accommodations/{id}/nameType")).permitAll();
                            auth.requestMatchers(antMatcher("/api/accommodations/{id}/nameAndType")).permitAll();
                            auth.requestMatchers(antMatcher("/api/users/{id}/usernameAndNumberOfCancellations")).permitAll();
                            auth.requestMatchers(antMatcher("/api/accommodations/{id}/cancellationDeadline")).permitAll();
                            auth.requestMatchers(antMatcher("/api/accommodations/{accommodationId}/ratingString")).permitAll();
                            auth.requestMatchers(antMatcher("/api/reviews/owner/{ownerId}/average-rating")).permitAll();
                            auth.requestMatchers(antMatcher("/api/owners/{id}/ratingString")).permitAll();
                            auth.requestMatchers(antMatcher("/api/accommodations/{id}/nameAndTypeAndBytes")).permitAll();
                            auth.requestMatchers(antMatcher("/api/users/{id}/basicInfo")).permitAll();
                            auth.requestMatchers(antMatcher("/api/users/{id}/password")).permitAll();
                            auth.requestMatchers(antMatcher("/api//users/androidToken/{token}")).permitAll();
                            auth.requestMatchers(antMatcher("/api/users/username/token/{token}")).permitAll();
                            auth.requestMatchers(antMatcher("/api/requests/guest/id/{id}")).permitAll();
                            auth.requestMatchers(antMatcher("/api/requests/owner/id/{id}")).permitAll();
                            auth.requestMatchers(antMatcher("/api/requests/filtered-host/{id}")).permitAll();
                    auth.requestMatchers(antMatcher("/api/requests/filtered-guest/{id}")).permitAll();

                            auth.requestMatchers(antMatcher("/api/accommodations/{id}/hasAcceptedReservation/{ownerId}")).hasAuthority("GUEST");
                    auth.requestMatchers(antMatcher("/api/reviews/hasAcceptedReservationForOwner")).hasAuthority("GUEST");
                    auth.requestMatchers(antMatcher("/api/accommodations/accommodationReviews")).hasAuthority("OWNER");
                            auth.requestMatchers(antMatcher("/api/users/favorite/{userId}")).permitAll();
                            //    api/reviewReports/reviews/isReported/{reviewId}
                    auth.requestMatchers(antMatcher("/api/ownerReviewReports/reviews/isReported")).hasAuthority("OWNER");
                    auth.requestMatchers(antMatcher("/api/reviewReports/reviews/isReported/{reviewId}")).hasAuthority("OWNER");
                    auth.requestMatchers(antMatcher("/api/accommodations/{accommodationId}/accommodationReviews/pending")).permitAll();
                    auth.requestMatchers(antMatcher("/api/accommodations/accommodationReviews/pending")).permitAll();

                    ///accommodationReviews/{reviewId}
                    auth.requestMatchers(antMatcher("/api/accommodationReviews/{reviewId}")).hasAnyAuthority("GUEST", "ADMIN");
                            auth.requestMatchers(antMatcher("/api/users/owner/{userId}")).permitAll();
                            auth.requestMatchers(antMatcher("/api/accommodation-reports/{ownerId}")).permitAll();
                            auth.requestMatchers(antMatcher("/api/accommodation-reports/{accommodationId}/monthly-report")).permitAll();
                            //api/accommodations/{accommodationId}/average-rating
                    auth.requestMatchers(antMatcher("/api/accommodation/{accommodationId}/average-rating")).permitAll();
                    auth.requestMatchers(antMatcher("/api/users/{userId}/not-wanted-notifications")).hasAnyAuthority("OWNER", "GUEST");
                    auth.requestMatchers(antMatcher("/api/notifications/{userId}/{setSeen}")).hasAnyAuthority("OWNER", "GUEST");
                            auth.requestMatchers(antMatcher("/error")).permitAll();
                            auth.requestMatchers(antMatcher("/socket")).permitAll();
                            auth.requestMatchers(antMatcher("/socket/*")).permitAll();
                            auth.requestMatchers(antMatcher("/socket/**")).permitAll();
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
                        antMatcher(HttpMethod.GET, "/api/users/{id}/image-type-username"),
                        antMatcher("/error"),
                        antMatcher("/socket"),
                        antMatcher("/socket/*"),
                        antMatcher("/socket/**"),
                        antMatcher(HttpMethod.GET, "/api/owners/{id}/rating"),
                        antMatcher(HttpMethod.GET, "/api/accommodations/{accommodationId}/rating"),
                        antMatcher(HttpMethod.GET, "/api/accommodations/sort/rating/desc"),
                        antMatcher(HttpMethod.GET, "/api/accommodations/sort/rating/asc"),
                        antMatcher(HttpMethod.GET, "/api/accommodations/sort/price/desc"),
                        antMatcher(HttpMethod.DELETE, "/api/notifications/{notificationId}"),
                        antMatcher(HttpMethod.GET, "/api/accommodations/sort/price/asc"),
                        antMatcher(HttpMethod.POST, "/api/requests"), antMatcher(HttpMethod.GET, "/api/users/token/{token}"),
                        antMatcher(HttpMethod.GET,"/api/requests/guest/{id}"), antMatcher(HttpMethod.GET,"/api/requests/owner/{username}"),
                        antMatcher(HttpMethod.DELETE,"/api/requests/{id}"),
                        antMatcher(HttpMethod.PUT,"/api/users/{id}/favorite-accommodations/{accommodationId}"),
                        antMatcher(HttpMethod.DELETE,"/api/users/{id}/favorite-accommodations/{accommodationId}"),
                        antMatcher(HttpMethod.GET,"/api/users/favorite/{userId}"),
                        antMatcher(HttpMethod.GET,"/api/users/owner/{userId}"),
                        antMatcher(HttpMethod.PUT,"/api/guest/{id}"),
                        antMatcher(HttpMethod.GET, "/api/reviews/owner/{ownerId}"),
                        antMatcher(HttpMethod.GET, "/api/accommodations/accommodationReviews/pending"),
                        antMatcher(HttpMethod.GET, "/api/accommodations/{accommodationId}/accommodationReviews/pending"),
                        ///api/accommodations/{accommodationId}/average-rating
                        antMatcher(HttpMethod.GET, "/api/accommodation/{accommodationId}/average-rating"),
                        antMatcher(HttpMethod.GET, "/api/reviews/owner/{ownerId}/average-rating"));

    }

    // disable CORS for Android app
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**").allowedOrigins("http://localhost:4200","http://192.168.1.4").allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("*");;
//            }
//        };
//    }
}


