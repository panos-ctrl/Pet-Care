package petcare.web.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import petcare.core.service.PetDataService;
import petcare.core.service.model.PetView;

import java.util.List;

/**
 * REST controller για τη διαχείριση των κατοικίδιων (Pet resource).
 */
@RestController
@RequestMapping(value = "/api/v1/pet", produces = MediaType.APPLICATION_JSON_VALUE)
public class PetResource {

    private final PetDataService petDataService;

    public PetResource(final PetDataService petDataService) {
        if (petDataService == null) throw new NullPointerException();
        this.petDataService = petDataService;
    }

    // Επιστρέφει τη λίστα όλων των κατοικίδιων σε μορφή JSON
    @GetMapping("")
    public List<PetView> pets() {
        final List<PetView> petViewList = this.petDataService.getAllPets();
        return petViewList;
    }
}