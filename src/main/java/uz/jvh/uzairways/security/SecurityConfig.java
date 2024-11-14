package uz.jvh.uzairways.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return ((request, response, authException) -> {
            authException.printStackTrace();
            String errorPath = request.getRequestURI();
            String errorMessage = authException.getMessage();
            Integer errorCode = 401;
            AppErrorRequest appErrorDto = new AppErrorRequest(
                    errorPath, errorMessage, errorCode, LocalDateTime.now()
            );
            response.setStatus(errorCode);
            ServletOutputStream outputStream = response.getOutputStream();
            objectMapper.writeValue(outputStream, appErrorDto);
        });
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()  // CSRF ni o'chirish
                .cors().disable()  // CORS ni o'chirish (sizning iltimosingiz bo‘yicha)
                .authorizeHttpRequests()
                .requestMatchers("/test",
                        "/api/auth/register/**",
                        "/api/auth/login/**","/api/auth/register","/api/auth/login",
                        "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .and()
                .addFilterBefore(new JwtTokenFilter(jwtTokenUtil, userDetailsService),
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /*@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOriginPatterns(List.of("http://localhost:3000"));  // Frontend domenini qo'shish
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));  // Ruxsat etilgan metodlar
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));  // Ruxsat etilgan sarlavhalar
        corsConfiguration.setAllowCredentials(true);  // Cookie yoki auth ma'lumotlarini uzatishga ruxsat

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }*/


    @Bean
    public AuthenticationManager authManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder
                = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Getter
    @AllArgsConstructor
    public static class AppErrorRequest {
        private String errorPath;
        private String errorMessage;
        private Integer errorCode;
        private LocalDateTime timeStamp;
    }

  /*  @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://64.227.142.47:8081")); // Ruxsat berilgan manzillar
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }*/
}
