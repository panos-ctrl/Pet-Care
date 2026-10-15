package petcare.core.port.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import petcare.config.RestApiClientConfig;
import petcare.core.port.VaccineLookupPort;
import petcare.core.port.impl.dto.VaccineLookupResult;

import java.util.Collections;
import java.util.List;

/**
 * Υλοποίηση του VaccineLookupPort που καλεί την εξωτερική υπηρεσία μέσω RestTemplate.
 */
@Service
public class VaccineLookupPortImpl implements VaccineLookupPort {

    private final RestTemplate restTemplate;

    public VaccineLookupPortImpl(final RestTemplate restTemplate) {
        if (restTemplate == null) throw new NullPointerException();
        this.restTemplate = restTemplate;
    }

    @Override
    public List<String> getTypicalVaccines(final String species) {
        if (species == null) throw new NullPointerException();
        if (species.isBlank()) throw new IllegalArgumentException();

        // Κλήση HTTP GET προς το εξωτερικό REST API
        final String baseUrl = RestApiClientConfig.BASE_URL;
        final String url = baseUrl + "/api/v1/vaccines/lookup/" + species.trim();

        try {
            final ResponseEntity<VaccineLookupResult> response =
                    this.restTemplate.getForEntity(url, VaccineLookupResult.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                final VaccineLookupResult result = response.getBody();
                if (result == null || result.vaccines() == null) {
                    return Collections.emptyList();
                }
                return result.vaccines();
            }
        } catch (Exception e) {
            // Αν η εξωτερική υπηρεσία δεν απαντήσει, επιστρέφουμε προεπιλεγμένο μήνυμα ώστε να μην κρασάρει η εφαρμογή
            return List.of("Αδυναμία σύνδεσης με την υπηρεσία εμβολίων");
        }

        throw new RuntimeException("External service responded with error");
    }
}