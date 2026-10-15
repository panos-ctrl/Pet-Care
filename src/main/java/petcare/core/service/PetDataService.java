package petcare.core.service;

import petcare.core.model.Person;
import petcare.core.service.model.PetView;
import java.util.List;

public interface PetDataService {

    List<PetView> getAllPets();
    List<PetView> getPetsByOwner(Person owner);
}
