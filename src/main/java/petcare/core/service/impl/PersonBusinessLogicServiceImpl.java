package petcare.core.service.impl;

import petcare.core.model.Person;
import petcare.core.repository.PersonRepository;
import petcare.core.service.PersonBusinessLogicService;
import petcare.core.service.model.CreatePersonRequest;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonBusinessLogicServiceImpl implements PersonBusinessLogicService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    // Dependency Injection
    public PersonBusinessLogicServiceImpl(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public Person createPerson(CreatePersonRequest request) {

        // Έλεγχος αν το email υπάρχει ήδη στη βάση
        if (personRepository.findByEmailAddress(request.emailAddress()).isPresent()) {
            throw new RuntimeException("Αυτό το Email χρησιμοποιείται ήδη!");
            // Σημείωση: Στο μέλλον μπορούμε να επιστρέφουμε ένα Result object αντί για Exception, όπως έκανε το πρότυπο.
        }

        //  Κρυπτογράφηση του κωδικού (hash)
        String hashedPassword = passwordEncoder.encode(request.rawPassword());

        // Δημιουργία της οντότητας Person
        Person person = new Person();
        person.setFirstName(request.firstName());
        person.setLastName(request.lastName());
        person.setEmailAddress(request.emailAddress());
        person.setPhoneNumber(request.phoneNumber());
        person.setRole(request.role()); // π.χ. VET ή OWNER
        person.setPasswordHash(hashedPassword); // Σώζουμε ΜΟΝΟ τον κρυπτογραφημένο κωδικό

        // Αποθήκευση στη βάση δεδομένων
        return personRepository.save(person);
    }
}