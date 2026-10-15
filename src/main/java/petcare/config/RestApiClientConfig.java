package petcare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Ρύθμιση του RestTemplate για την επικοινωνία με εξωτερικές REST υπηρεσίες.
 */
@Configuration
public class RestApiClientConfig {

    // Η βασική διεύθυνση της εξωτερικής/mock υπηρεσίας
    public static final String BASE_URL = "http://localhost:8080";

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}