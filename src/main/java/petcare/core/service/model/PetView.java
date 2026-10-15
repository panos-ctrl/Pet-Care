package petcare.core.service.model;

import java.util.List;

// Record που κρατάει μόνο τα στοιχεία του κατοικίδιου και τα προτεινόμενα εμβόλια
public record PetView(
        Long id,
        String name,
        String species,
        String description,
        String ownerEmail,
        List<String>recommendedVaccines
) {
}