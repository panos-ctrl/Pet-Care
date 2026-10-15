package petcare.core.service;

import petcare.core.service.model.CreatePetRequest;

public interface PetBusinessLogicService {
    void createPet(CreatePetRequest request, String ownerEmail);
}