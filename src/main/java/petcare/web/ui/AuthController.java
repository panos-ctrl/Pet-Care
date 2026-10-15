package petcare.web.ui;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login(
            final Authentication authentication,
            final HttpServletRequest request,
            final Model model
    ) {
        // Αν είναι ήδη συνδεδεμένος, μην του δείχνεις το login, πήγαινέ τον στο dashboard
        if (AuthUtils.isAuthenticated(authentication)) {
            return "redirect:/dashboard";
        }

        // Μηνύματα από το Spring Security
        if (request.getParameter("error") != null) {
            model.addAttribute("error", "Λάθος email ή κωδικός.");
        }
        if (request.getParameter("logout") != null) {
            model.addAttribute("message", "Αποσυνδεθήκατε επιτυχώς.");
        }
        return "login"; // Δείχνει το αρχείο login.html
    }

    @GetMapping("/logout")
    public String logout(final Authentication authentication) {
        if (AuthUtils.isAnonymous(authentication)) {
            return "redirect:/login";
        }
        return "logout"; // Δείχνει το αρχείο logout.html
    }
}