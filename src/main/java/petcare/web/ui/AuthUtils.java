package petcare.web.ui;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

public class AuthUtils {

    // Ελέγχει αν ο χρήστης είναι συνδεδεμένος
    public static boolean isAuthenticated(Authentication authentication) {
        return authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
    }

    // Ελέγχει αν ο χρήστης είναι απλός επισκέπτης (όχι συνδεδεμένος)
    public static boolean isAnonymous(Authentication authentication) {
        return !isAuthenticated(authentication);
    }
}