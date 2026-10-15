package petcare.web.ui;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import petcare.core.model.Person;
import petcare.core.model.Role;
import petcare.core.repository.PersonRepository;
import petcare.core.repository.PetRepository;
import petcare.core.service.AppointmentBusinessLogicService;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentBusinessLogicService appointmentService;
    private final PersonRepository personRepository;
    private final PetRepository petRepository;

    public AppointmentController(AppointmentBusinessLogicService appointmentService, PersonRepository personRepository, PetRepository petRepository) {
        this.appointmentService = appointmentService;
        this.personRepository = personRepository;
        this.petRepository = petRepository;
    }

    // Λίστα Ραντεβού
    @GetMapping
    public String listAppointments(Authentication auth, Model model) {
        Person me = personRepository.findByEmailAddress(auth.getName()).orElse(null);
        if (me == null) return "redirect:/login";

        model.addAttribute("me", me);
        model.addAttribute("appointments", appointmentService.getAppointmentsFor(me));
        return "appointments";
    }

    // Φόρμα Νέου Ραντεβού (Μόνο για ιδιοκτήτες)
    @GetMapping("/new")
    public String newAppointmentForm(Authentication auth, Model model) {
        Person me = personRepository.findByEmailAddress(auth.getName()).orElse(null);
        if (me == null) return "redirect:/login";

        model.addAttribute("me", me);
        model.addAttribute("myPets", petRepository.findByOwner(me));

        // Ζητάμε από τη βάση δεδομένων μόνο τους Κτηνιάτρους
        model.addAttribute("vets", personRepository.findByRole(Role.VET));

        return "new_appointment";
    }

    // Αποθήκευση Νέου Ραντεβού
    @PostMapping("/new")
    public String saveAppointment(@RequestParam Long petId, @RequestParam Long vetId, @RequestParam String appointmentDate) {
        appointmentService.createAppointment(petId, vetId, appointmentDate);
        return "redirect:/appointments";
    }

    // Ενημέρωση Κατάστασης (Από Κτηνίατρο)
    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam String status) {
        appointmentService.updateStatus(id, status);
        return "redirect:/appointments";
    }
}