package ma.projet.classes;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "projets")
public class Projet {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) private String nom;
    @Column(name = "date_debut", nullable = false) private LocalDate dateDebut;
    @ManyToOne(optional = false)
    @JoinColumn(name = "gestionnaire_id", nullable = false)
    private Employe gestionnaire;

    protected Projet() {}
    public Projet(String nom, LocalDate dateDebut, Employe gestionnaire) {
        this.nom = nom; this.dateDebut = dateDebut; this.gestionnaire = gestionnaire;
    }
    public Long getId() { return id; }
    public String getNom() { return nom; }
    public LocalDate getDateDebut() { return dateDebut; }
    public Employe getGestionnaire() { return gestionnaire; }
    public void setNom(String nom) { this.nom = nom; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    public void setGestionnaire(Employe gestionnaire) { this.gestionnaire = gestionnaire; }
}
