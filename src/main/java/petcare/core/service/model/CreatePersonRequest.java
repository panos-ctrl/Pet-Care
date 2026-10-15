package petcare.core.service.model;

import petcare.core.model.Role;

public record CreatePersonRequest(
        String firstName,
        String lastName,
        String emailAddress,
        String phoneNumber,
        Role role,
        String rawPassword // Ο κωδικός όπως τον πληκτρολογεί ο χρήστης (σε απλό κείμενο)
) {
}