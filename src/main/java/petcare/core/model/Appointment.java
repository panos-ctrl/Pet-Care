package petcare.core.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Το ραντεβού αφορά ένα συγκεκριμένο κατοικίδιο
    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    // Το ραντεβού κλείνεται με έναν συγκεκριμένο κτηνίατρο (Person)
    @ManyToOne
    @JoinColumn(name = "vet_id", nullable = false)
    private Person vet;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime appointmentDate;

    @Column(nullable = false)
    private String status = "ΕΚΚΡΕΜΕΙ"; // ΕΚΚΡΕΜΕΙ, ΕΠΙΒΕΒΑΙΩΘΗΚΕ, ΑΚΥΡΩΘΗΚΕ

    @Column(length = 500)
    private String vetNotes; // Σημειώσεις του κτηνιάτρου μετά την εξέταση

    public Appointment() {}

    // Getters και Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Pet getPet() { return pet; }
    public void setPet(Pet pet) { this.pet = pet; }

    public Person getVet() { return vet; }
    public void setVet(Person vet) { this.vet = vet; }

    public LocalDateTime getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(LocalDateTime appointmentDate) { this.appointmentDate = appointmentDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getVetNotes() { return vetNotes; }
    public void setVetNotes(String vetNotes) { this.vetNotes = vetNotes; }
}