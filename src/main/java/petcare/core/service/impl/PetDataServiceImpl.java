package petcare.core.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petcare.core.model.Person;
import petcare.core.port.VaccineLookupPort;
import petcare.core.repository.PetRepository;
import petcare.core.service.PetDataService;
import petcare.core.service.model.PetView;

import java.util.List;

@Service
public class PetDataServiceImpl implements PetDataService {

    private final PetRepository petRepository;
    private final VaccineLookupPort vaccineLookupPort;

    public PetDataServiceImpl(final PetRepository petRepository, final VaccineLookupPort vaccineLookupPort) {
        if (petRepository == null) throw new NullPointerException();
        if (vaccineLookupPort == null) throw new NullPointerException();
        this.petRepository = petRepository;
        this.vaccineLookupPort = vaccineLookupPort;
    }

    @Transactional(readOnly = true)
    @Override
    public List<PetView> getAllPets() {
        // Παίρνουμε τα κατοικίδια από τη βάση και για το καθένα καλούμε το εξωτερικό API εμβολίων
        return this.petRepository.findAll().stream()
                .map(pet -> new PetView(
                        pet.getId(),
                        pet.getName(),
                        pet.getSpecies(),
                        pet.getDescription(),
                        pet.getOwner().getEmailAddress(),
                        this.vaccineLookupPort.getTypicalVaccines(pet.getSpecies())
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<PetView> getPetsByOwner(final Person owner) {
        // Παίρνουμε μόνο τα κατοικίδια του ιδιοκτήτη και καλούμε το εξωτερικό API εμβολίων για το καθένα
        return this.petRepository.findByOwner(owner).stream()
                .map(pet -> new PetView(
                        pet.getId(),
                        pet.getName(),
                        pet.getSpecies(),
                        pet.getDescription(),
                        pet.getOwner().getEmailAddress(),
                        this.vaccineLookupPort.getTypicalVaccines(pet.getSpecies())
                ))
                .toList();
    }

}