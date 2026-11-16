package ma.projet.classes;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "mariages")
public class Mariage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false) @JoinColumn(name = "homme_id", nullable = false)
    private Homme homme;
    @ManyToOne(optional = false) @JoinColumn(name = "femme_id", nullable = false)
    private Femme femme;
    @Column(name = "date_debut", nullable = false) private LocalDate dateDebut;
    @Column(name = "date_fin") private LocalDate dateFin;
    @Column(name = "nombre_enfants", nullable = false) private int nombreEnfants;

    protected Mariage() {}
    public Mariage(Homme homme, Femme femme, LocalDate dateDebut, LocalDate dateFin, int nombreEnfants) {
        if (nombreEnfants < 0) throw new IllegalArgumentException("Nombre d'enfants négatif");
        if (dateFin != null && dateFin.isBefore(dateDebut)) throw new IllegalArgumentException("Dates de mariage inversées");
        this.homme = homme; this.femme = femme; this.dateDebut = dateDebut;
        this.dateFin = dateFin; this.nombreEnfants = nombreEnfants;
    }
    public Long getId() { return id; }
    public Homme getHomme() { return homme; }
    public Femme getFemme() { return femme; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public int getNombreEnfants() { return nombreEnfants; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    public void setNombreEnfants(int nombreEnfants) {
        if (nombreEnfants < 0) throw new IllegalArgumentException("Nombre d'enfants négatif");
        this.nombreEnfants = nombreEnfants;
    }
}
