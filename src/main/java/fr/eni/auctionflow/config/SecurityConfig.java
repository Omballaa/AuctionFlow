package fr.eni.auctionflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/utilisateurs/inscription", "/utilisateurs/connexion").permitAll() // Pages accessibles sans connexion
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/utilisateurs/connexion")
                        .defaultSuccessUrl("/accueil")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/utilisateurs/deconnexion")
                        .logoutSuccessUrl("/accueil")
                        .permitAll()
                )
                .rememberMe(remember -> remember
                        .key("uniqueAndSecret")
                        .tokenValiditySeconds(86400) // 1 jour
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
