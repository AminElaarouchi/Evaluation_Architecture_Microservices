package ma.projet.classes;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "taches")
@NamedQuery(name = "Tache.prixSuperieurA", query = "select t from Tache t where t.prix > :minimum order by t.nom")
public class Tache {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) private String nom;
    @Column(nullable = false, precision = 12, scale = 2) private BigDecimal prix;
    @Column(name = "debut_planifie", nullable = false) private LocalDate debutPlanifie;
    @Column(name = "fin_planifie", nullable = false) private LocalDate finPlanifie;
    @ManyToOne(optional = false)
    @JoinColumn(name = "projet_id", nullable = false)
    private Projet projet;

    protected Tache() {}
    public Tache(String nom, BigDecimal prix, LocalDate debutPlanifie, LocalDate finPlanifie, Projet projet) {
        this.nom = nom; this.prix = prix; this.debutPlanifie = debutPlanifie;
        this.finPlanifie = finPlanifie; this.projet = projet;
    }
    public Long getId() { return id; }
    public String getNom() { return nom; }
    public BigDecimal getPrix() { return prix; }
    public LocalDate getDebutPlanifie() { return debutPlanifie; }
    public LocalDate getFinPlanifie() { return finPlanifie; }
    public Projet getProjet() { return projet; }
    public void setPrix(BigDecimal prix) { this.prix = prix; }
    public void setNom(String nom) { this.nom = nom; }
}
