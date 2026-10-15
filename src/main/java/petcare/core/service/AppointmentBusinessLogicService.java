package petcare.core.service;

import org.springframework.stereotype.Service;
import petcare.core.model.Appointment;
import petcare.core.model.Person;
import petcare.core.model.Pet;
import petcare.core.repository.AppointmentRepository;
import petcare.core.repository.PersonRepository;
import petcare.core.repository.PetRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentBusinessLogicService {

    private final AppointmentRepository appointmentRepository;
    private final PetRepository petRepository;
    private final PersonRepository personRepository;

    public AppointmentBusinessLogicService(AppointmentRepository appointmentRepository, PetRepository petRepository, PersonRepository personRepository) {
        this.appointmentRepository = appointmentRepository;
        this.petRepository = petRepository;
        this.personRepository = personRepository;
    }

    // Φέρνει τα ραντεβού ανάλογα με τον ρόλο
    public List<Appointment> getAppointmentsFor(Person person) {
        if ("VET".equals(String.valueOf(person.getType()))) {
            return appointmentRepository.findByVet(person);
        } else {
            return appointmentRepository.findByPet_Owner(person);
        }
    }

    // Δημιουργία ραντεβού από τον ιδιοκτήτη
    public void createAppointment(Long petId, Long vetId, String dateTimeStr) {
        Pet pet = petRepository.findById(petId).orElseThrow();
        Person vet = personRepository.findById(vetId).orElseThrow();

        Appointment appt = new Appointment();
        appt.setPet(pet);
        appt.setVet(vet);
        appt.setAppointmentDate(LocalDateTime.parse(dateTimeStr)); // Format: 2026-10-15T10:30
        appt.setStatus("ΕΚΚΡΕΜΕΙ");

        appointmentRepository.save(appt);
    }

    // Αλλαγή κατάστασης από τον κτηνίατρο
    public void updateStatus(Long appointmentId, String newStatus) {
        Appointment appt = appointmentRepository.findById(appointmentId).orElseThrow();
        appt.setStatus(newStatus);
        appointmentRepository.save(appt);
    }
}