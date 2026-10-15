package petcare.core.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import petcare.core.model.Person;
import petcare.core.model.Pet;
import petcare.core.repository.PersonRepository;
import petcare.core.repository.PetRepository;
import petcare.core.service.PetBusinessLogicService;
import petcare.core.service.model.CreatePetRequest;

@Service
public class PetBusinessLogicServiceImpl implements PetBusinessLogicService {

    private final PetRepository petRepository;
    private final PersonRepository personRepository;

    public PetBusinessLogicServiceImpl(PetRepository petRepository, PersonRepository personRepository) {
        this.petRepository = petRepository;
        this.personRepository = personRepository;
    }

    @Transactional
    @Override
    public void createPet(CreatePetRequest request, String ownerEmail) {
        // Βρίσκουμε ποιος είναι συνδεδεμένος
        Person owner = personRepository.findByEmailAddress(ownerEmail).orElseThrow();

        // Δημιουργούμε το κατοικίδιο
        Pet pet = new Pet();
        pet.setName(request.name());
        pet.setSpecies(request.species());
        pet.setDescription(request.description());
        pet.setOwner(owner);

        petRepository.save(pet);
    }
}