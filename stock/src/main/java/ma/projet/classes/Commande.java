package ma.projet.classes;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "commandes")
public class Commande {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date_commande", nullable = false)
    private LocalDate dateCommande;

    protected Commande() {}
    public Commande(LocalDate dateCommande) { this.dateCommande = dateCommande; }
    public Long getId() { return id; }
    public LocalDate getDateCommande() { return dateCommande; }
    public void setDateCommande(LocalDate dateCommande) { this.dateCommande = dateCommande; }
}
