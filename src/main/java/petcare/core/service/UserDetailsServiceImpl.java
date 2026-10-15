package petcare.core.service;

import petcare.core.model.Person;
import petcare.core.repository.PersonRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PersonRepository personRepository;

    // Dependency Injection (όπως το έκανε και ο κώδικας-πρότυπο)
    public UserDetailsServiceImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //  Ψάχνουμε τον χρήστη στη βάση με το email του
        Person person = personRepository.findByEmailAddress(email)
                .orElseThrow(() -> new UsernameNotFoundException("Δεν βρέθηκε χρήστης με email: " + email));

        //  Μετατρέπουμε τον δικό μας "Person" στο αντικείμενο "User" που ζητάει το Spring Security
        return new org.springframework.security.core.userdetails.User(
                person.getEmailAddress(),
                person.getPasswordHash(), // Το κρυπτογραφημένο password
                Collections.singletonList(new SimpleGrantedAuthority(person.getRole().name())) // Ο ρόλος του (VET ή OWNER)
        );
    }
}