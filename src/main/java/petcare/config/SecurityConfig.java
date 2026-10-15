package petcare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Απενεργοποίηση CSRF για την απρόσκοπτη λειτουργία των REST APIs και της H2 Console
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Ελεύθερη πρόσβαση στη βάση δεδομένων (H2 Console)
                        .requestMatchers("/h2-console/**").permitAll()
                        //   σε βασικά endpoints (εγγραφή, σύνδεση, APIs) και στατικά αρχεία
                        .requestMatchers("/", "/register", "/login", "/api/**", "/css/**", "/js/**").permitAll()
                        //   στην τεκμηρίωση του API μέσω Swagger UI / OpenAPI
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        // Προστασία διαδρομών βάσει του ρόλου του χρήστη
                        .requestMatchers("/vet/**").hasAuthority("VET")
                        .requestMatchers("/owner/**").hasAuthority("OWNER")
                        // Οποιαδήποτε άλλη κλήση απαιτεί αυθεντικοποίηση
                        .anyRequest().authenticated()
                )
                // Ενεργοποίηση X-Frame-Options
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .formLogin(form -> form
                        // Χρήση προσαρμοσμένης σελίδας σύνδεσης
                        .loginPage("/login")
                        // Ανακατεύθυνση στο κεντρικό dashboard μετά από επιτυχή σύνδεση
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll()
                )
                .logout(logout -> logout.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Αλγόριθμος BCrypt για την κρυπτογράφηση των κωδικών πρόσβασης στη βάση δεδομένων
        return new BCryptPasswordEncoder();
    }
}