package petcare.web.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import petcare.core.port.impl.dto.VaccineLookupResult;

import java.util.List;

/**
 * Mock εξωτερικό REST API που επιστρέφει τη λίστα τυπικών εμβολίων ανά είδος ζώου.
 */
@RestController
@RequestMapping(value = "/api/v1/vaccines", produces = MediaType.APPLICATION_JSON_VALUE)
public class MockVaccineResource {

    @GetMapping("/lookup/{species}")
    public VaccineLookupResult lookupVaccines(@PathVariable final String species) {
        final String normalized = species.trim().toLowerCase();

        // Ανάλογα με το είδος του ζώου, το API επιστρέφει τα αντίστοιχα τυπικά εμβόλια
        if (normalized.contains("σκύλ") || normalized.contains("σκυλ") || normalized.contains("dog")) {
            return new VaccineLookupResult(
                    species,
                    true,
                    List.of("Αντιλυσσικό", "Παρβοεντερίτιδα ", "Λεπτοσπείρωση")
            );
        } else if (normalized.contains("γάτ") || normalized.contains("γατ") || normalized.contains("cat")) {
            return new VaccineLookupResult(
                    species,
                    true,
                    List.of("Αντιλυσσικό", " Πανλευκοπενίας ", "Λευχαιμία Γάτας ")
            );
        } else {
            return new VaccineLookupResult(
                    species,
                    true,
                    List.of("Γενικός Κτηνιατρικός Έλεγχος", "Αντιλυσσικό (εφόσον απαιτείται)")
            );
        }
    }
}