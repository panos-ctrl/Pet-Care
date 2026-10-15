package petcare.web.ui;

import petcare.core.model.Role;
import petcare.core.service.PersonBusinessLogicService;
import petcare.core.service.model.CreatePersonRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final PersonBusinessLogicService personBusinessLogicService;

    public RegistrationController(final PersonBusinessLogicService personBusinessLogicService) {
        this.personBusinessLogicService = personBusinessLogicService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(final Authentication authentication, final Model model) {
        if (AuthUtils.isAuthenticated(authentication)) {
            return "redirect:/dashboard";
        }
        // Αρχικά δεδομένα για τη φόρμα (κενά πεδία, και προεπιλογή ρόλου OWNER)
        final CreatePersonRequest createPersonRequest = new CreatePersonRequest("", "", "", "", Role.OWNER, "");
        model.addAttribute("createPersonRequest", createPersonRequest);
        return "register"; // Δείχνει το αρχείο register.html
    }

    @PostMapping("/register")
    public String handleFormSubmission(
            final Authentication authentication,
            @ModelAttribute("createPersonRequest") final CreatePersonRequest createPersonRequest,
            final Model model
    ) {
        if (AuthUtils.isAuthenticated(authentication)) {
            return "redirect:/dashboard";
        }

        try {
            // Προσπαθούμε να αποθηκεύσουμε τον χρήστη
            this.personBusinessLogicService.createPerson(createPersonRequest);
            return "redirect:/login"; // Εγγραφή επιτυχής - πάμε στο login

        } catch (RuntimeException e) {
            // Αν το email υπάρχει ήδη, το Service θα πετάξει το RuntimeException που γράψαμε.
            // Κρατάμε τα στοιχεία που έγραψε στη φόρμα για να μην τα ξαναγράφει:
            model.addAttribute("createPersonRequest", createPersonRequest);
            // Του δείχνουμε το μήνυμα λάθους:
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
    }
}