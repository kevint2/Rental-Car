package com.sda.rentalcar.config;


import com.sda.rentalcar.repositories.EmployeeRepository;
import com.sda.rentalcar.security.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Value("${frontend.url}")
    private String frontEndUrl;
    private final String[] unAuthenticatedEndpoints = {"employee/login", "branch/getAllBranches",
            "car/getAllCarAvailableByBranch", "car/findAllByModel", "/swagger-ui.html",
            "swagger-ui/**", "/v3/api-docs/**"};
    private final String[] ownerEndpoints = {"branch/create", "employee/createManager",
            "costumer/getAllCostumers"};
    private final String[] managerEndpoints = {"employee/createEmployee", "car/create"};
    private final String[] employeeEndpoints = {"reservation/create","costumer/create", "costumer/findByEmail" +
            "", "reservation/cancelReservation",
            "reservation/returnCar", "car/updateStatusToUnavailable"};
    private final String[] adminEndpoints = {"rental/create", "employee/createOwner"};

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins(frontEndUrl);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
        // Spring boot 2.x
//        httpSecurity.authorizeRequests().requestMatchers("/user/register").permitAll()
//                        .anyRequest().authenticated();
        httpSecurity.authorizeHttpRequests(request -> request
                        .requestMatchers(unAuthenticatedEndpoints).permitAll()
                        .requestMatchers(adminEndpoints).hasRole("ADMIN")
                        .requestMatchers(ownerEndpoints).hasRole("OWNER")
                        .requestMatchers(managerEndpoints).hasRole("MANAGER")
                        .requestMatchers(employeeEndpoints).hasRole("EMPLOYEE")
                        .anyRequest().authenticated())
                .authenticationManager(authenticationManager)
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
