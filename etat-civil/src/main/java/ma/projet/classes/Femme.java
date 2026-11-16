package ma.projet.classes;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "femmes")
@NamedQuery(name = "Femme.marieesDeuxFois", query = "select f from Femme f where (select count(m) from Mariage m where m.femme = f) >= 2 order by f.nom")
@NamedNativeQuery(name = "Femme.nombreEnfantsEntre", query = "select coalesce(sum(m.nombre_enfants), 0) from mariages m where m.femme_id = :femmeId and m.date_debut between :debut and :fin")
public class Femme {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) private String nom;
    @Column(nullable = false) private String prenom;
    @Column(name = "date_naissance", nullable = false) private LocalDate dateNaissance;

    protected Femme() {}
    public Femme(String nom, String prenom, LocalDate dateNaissance) {
        this.nom = nom; this.prenom = prenom; this.dateNaissance = dateNaissance;
    }
    public Long getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
}
