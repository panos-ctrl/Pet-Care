package petcare.core.service;

import petcare.core.model.Person;
import petcare.core.service.model.CreatePersonRequest;

public interface PersonBusinessLogicService {
    // Η μέθοδος που θα υλοποιήσουμε για την εγγραφή
    Person createPerson(CreatePersonRequest request);
}