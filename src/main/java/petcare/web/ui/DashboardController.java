package petcare.web.ui;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import petcare.core.model.Person;
import petcare.core.repository.PersonRepository;

@Controller
public class DashboardController {

    private final PersonRepository personRepository;

    public DashboardController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/dashboard")
    public String showDashboard(final Authentication authentication, HttpServletRequest request, final Model model) {
        if (AuthUtils.isAnonymous(authentication)) return "redirect:/login";

        Person me = personRepository.findByEmailAddress(authentication.getName()).orElse(null);
        if (me == null) {
            try {
                request.logout(); // σκοτώνει το παλιό session του browser
            } catch (ServletException e) {

            }
            return "redirect:/register"; // Σε στέλνει κατευθείαν για νέα εγγραφή
        }

        model.addAttribute("me", me);
        return "dashboard";
    }
}