package ma.projet.classes;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "employe_taches")
public class EmployeTache {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false) @JoinColumn(name = "employe_id", nullable = false)
    private Employe employe;
    @ManyToOne(optional = false) @JoinColumn(name = "tache_id", nullable = false)
    private Tache tache;
    @Column(name = "debut_reel", nullable = false) private LocalDate debutReel;
    @Column(name = "fin_reelle", nullable = false) private LocalDate finReelle;

    protected EmployeTache() {}
    public EmployeTache(Employe employe, Tache tache, LocalDate debutReel, LocalDate finReelle) {
        if (finReelle.isBefore(debutReel)) throw new IllegalArgumentException("Dates réelles inversées");
        this.employe = employe; this.tache = tache; this.debutReel = debutReel; this.finReelle = finReelle;
    }
    public Long getId() { return id; }
    public Employe getEmploye() { return employe; }
    public Tache getTache() { return tache; }
    public LocalDate getDebutReel() { return debutReel; }
    public LocalDate getFinReelle() { return finReelle; }
}
