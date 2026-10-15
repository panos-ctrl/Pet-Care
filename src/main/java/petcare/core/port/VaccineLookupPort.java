package petcare.core.port;

import java.util.List;

/**
 * Port προς την εξωτερική υπηρεσία για την εύρεση τυπικών εμβολίων ανά είδος ζώου.
 */
public interface VaccineLookupPort {

    List<String> getTypicalVaccines(final String species);
}