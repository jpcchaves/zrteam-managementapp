package br.com.zrteam.managementapp.infra.config.security;

import br.com.zrteam.managementapp.presentation.error.CustomAccessDeniedHandler;
import br.com.zrteam.managementapp.presentation.security.JwtAuthenticationEntrypoint;
import br.com.zrteam.managementapp.presentation.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter authenticationFilter;
    private final JwtAuthenticationEntrypoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Value("${app.security.public-endpoints")
    private String[] publicEndpoints;

    public SecurityConfig(
            JwtAuthenticationFilter authenticationFilter,
            JwtAuthenticationEntrypoint authenticationEntryPoint,
            CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.authenticationFilter = authenticationFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .headers((headers) ->
                        headers
                                .contentTypeOptions(Customizer.withDefaults())
                                .xssProtection(Customizer.withDefaults())
                                .cacheControl(Customizer.withDefaults())
                                .httpStrictTransportSecurity(Customizer.withDefaults())
                                .frameOptions(Customizer.withDefaults())
                                .disable())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(
                                        publicEndpoints
                                )
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .exceptionHandling(exception ->
                        exception
                                .authenticationEntryPoint(authenticationEntryPoint)
                                .accessDeniedHandler(customAccessDeniedHandler)
                )
                .sessionManagement(session ->
                        session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .cors(Customizer.withDefaults());

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
