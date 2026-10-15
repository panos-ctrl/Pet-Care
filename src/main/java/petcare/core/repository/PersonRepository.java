package petcare.core.repository;

import petcare.core.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import petcare.core.model.Role;

import java.util.Optional;
import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    // Αυτή η μέθοδος ψάχνει αυτόματα στη βάση να βρει έναν χρήστη από το email του
    Optional<Person> findByEmailAddress(String emailAddress);

    // Φέρνει κατευθείαν από τη βάση μόνο όσους έχουν τον ρόλο που ζητάμε
    List<Person> findByRole(Role role);
}