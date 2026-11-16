package ma.projet.classes;

import javax.persistence.*;

@Entity
@Table(name = "lignes_commande", uniqueConstraints = @UniqueConstraint(columnNames = {"commande_id", "produit_id"}))
public class LigneCommande {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;
    @ManyToOne(optional = false)
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;
    @Column(nullable = false)
    private int quantite;

    protected LigneCommande() {}
    public LigneCommande(Commande commande, Produit produit, int quantite) {
        if (quantite <= 0) throw new IllegalArgumentException("La quantité doit être positive");
        this.commande = commande; this.produit = produit; this.quantite = quantite;
    }
    public Long getId() { return id; }
    public Commande getCommande() { return commande; }
    public Produit getProduit() { return produit; }
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) {
        if (quantite <= 0) throw new IllegalArgumentException("La quantité doit être positive");
        this.quantite = quantite;
    }
}
