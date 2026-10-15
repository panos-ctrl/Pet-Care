package petcare.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import petcare.core.model.Appointment;
import petcare.core.model.Person;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Φέρνει τα ραντεβού ενός συγκεκριμένου κτηνιάτρου
    List<Appointment> findByVet(Person vet);

    // Φέρνει τα ραντεβού για τα κατοικίδια ενός συγκεκριμένου ιδιοκτήτη
    List<Appointment> findByPet_Owner(Person owner);
}