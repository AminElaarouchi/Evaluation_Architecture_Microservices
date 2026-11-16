package ma.projet.classes;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "hommes")
public class Homme {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) private String nom;
    @Column(nullable = false) private String prenom;
    @Column(name = "date_naissance", nullable = false) private LocalDate dateNaissance;

    protected Homme() {}
    public Homme(String nom, String prenom, LocalDate dateNaissance) {
        this.nom = nom; this.prenom = prenom; this.dateNaissance = dateNaissance;
    }
    public Long getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
}
