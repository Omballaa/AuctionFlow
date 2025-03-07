package fr.eni.auctionflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                        .csrf(csrf -> csrf.disable()) // Désactiver CSRF temporairement
                        .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/*", "/utilisateurs/inscription", "/utilisateurs/connexion").permitAll()
                                .requestMatchers(HttpMethod.POST, "/ventes/ajouter").authenticated()
                                .anyRequest().authenticated()
                        )
                        .formLogin(login -> login
                                .loginPage("/utilisateurs/connexion")
                                .loginProcessingUrl("/login")
                                .successHandler((request, response, authentication) -> {
                                        request.getSession().setAttribute("message", "Connexion réussie !");
                                        response.sendRedirect("/");
                                })
                                .failureUrl("/utilisateurs/connexion?invalidCredentials")
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
                        )
                        .exceptionHandling(exception -> exception
                                .accessDeniedPage("/erreur/acces-refuse") // Redirection si l'accès est refusé
                        )
                        .sessionManagement(session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Garder la session si nécessaire
                                .invalidSessionUrl("/utilisateurs/connexion") // Redirection vers la page de connexion si la session est invalide
                                .maximumSessions(1) // Limiter à une seule session par utilisateur
                                .maxSessionsPreventsLogin(true) // Empêcher l'utilisateur de se connecter si une session est déjà ouverte
                        );

                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}

