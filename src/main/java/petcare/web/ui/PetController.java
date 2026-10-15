package petcare.web.ui;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import petcare.core.model.Person;
import petcare.core.repository.PersonRepository;
import petcare.core.service.PetBusinessLogicService;
import petcare.core.service.PetDataService;
import petcare.core.service.model.CreatePetRequest;

@Controller
public class PetController {

    private final PetBusinessLogicService petBusinessLogicService;
    private final PersonRepository personRepository;
    private final PetDataService petDataService;

    public PetController(
            PetBusinessLogicService petBusinessLogicService,
            PersonRepository personRepository,
            PetDataService petDataService
    ) {
        this.petBusinessLogicService = petBusinessLogicService;
        this.personRepository = personRepository;
        this.petDataService = petDataService;
    }

    @GetMapping("/pets")
    public String listPets(final Authentication authentication, HttpServletRequest request, final Model model) {
        if (AuthUtils.isAnonymous(authentication)) return "redirect:/login";

        Person me = personRepository.findByEmailAddress(authentication.getName()).orElse(null);
        if (me == null) {
            try { request.logout(); } catch (ServletException e) {}
            return "redirect:/register";
        }

        model.addAttribute("me", me);

        // Αν ο χρήστης είναι Κτηνίατρος (VET), βλέπει όλα τα κατοικίδια όλων των πελατών μέσω του DataService/API
        if ("VET".equals(String.valueOf(me.getType()))) {
            model.addAttribute("pets", petDataService.getAllPets());
        } else {
            // Αν είναι Ιδιοκτήτης (OWNER), βλέπει μόνο τα δικά του κατοικίδια
            model.addAttribute("pets", petDataService.getPetsByOwner(me));
        }

        return "pets";
    }

    @GetMapping("/pets/new")
    public String showNewPetForm(final Authentication authentication, HttpServletRequest request, final Model model) {
        if (AuthUtils.isAnonymous(authentication)) return "redirect:/login";

        Person me = personRepository.findByEmailAddress(authentication.getName()).orElse(null);
        if (me == null) {
            try { request.logout(); } catch (ServletException e) {}
            return "redirect:/register";
        }

        model.addAttribute("me", me);
        model.addAttribute("form", new CreatePetRequest("", "", ""));
        return "new_pet";
    }

    @PostMapping("/pets/new")
    public String handleNewPetSubmission(
            final Authentication authentication,
            HttpServletRequest request,
            @Valid @ModelAttribute("form") final CreatePetRequest form,
            final BindingResult bindingResult,
            final Model model
    ) {
        if (AuthUtils.isAnonymous(authentication)) return "redirect:/login";

        Person me = personRepository.findByEmailAddress(authentication.getName()).orElse(null);
        if (me == null) {
            try { request.logout(); } catch (ServletException e) {}
            return "redirect:/register";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("me", me);
            return "new_pet";
        }

        petBusinessLogicService.createPet(form, authentication.getName());
        return "redirect:/pets";
    }
}