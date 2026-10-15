package petcare.core.port.impl.dto;

import java.util.List;

/**
 * DTO που αναπαριστά την απάντηση (JSON) της εξωτερικής υπηρεσίας εμβολίων.
 */
public record VaccineLookupResult(
        String species,
        boolean exists,
        List<String> vaccines
) {
}