package petcare.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import petcare.core.model.Pet;
import petcare.core.model.Person;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    // Φέρνει μόνο τα κατοικίδια που ανήκουν στον συγκεκριμένο ιδιοκτήτη
    List<Pet> findByOwner(Person owner);
}