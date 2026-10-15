package petcare.core.service.model;

import jakarta.validation.constraints.NotBlank;

public record CreatePetRequest(
        @NotBlank(message = "Το όνομα είναι υποχρεωτικό") String name,
        @NotBlank(message = "Το είδος είναι υποχρεωτικό") String species,
        @NotBlank(message = "Η περιγραφή είναι υποχρεωτική") String description
) {
}