package ma.projet.classes;

import java.math.BigDecimal;
import javax.persistence.*;

@Entity
@Table(name = "produits")
@NamedQuery(name = "Produit.prixSuperieurA", query = "select p from Produit p where p.prix > :minimum order by p.reference")
public class Produit {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String reference;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal prix;
    @Column(nullable = false)
    private int stock;
    @ManyToOne(optional = false)
    @JoinColumn(name = "categorie_id", nullable = false)
    private Categorie categorie;

    protected Produit() {}
    public Produit(String reference, BigDecimal prix, int stock, Categorie categorie) {
        this.reference = reference; this.prix = prix; this.stock = stock; this.categorie = categorie;
    }
    public Long getId() { return id; }
    public String getReference() { return reference; }
    public BigDecimal getPrix() { return prix; }
    public int getStock() { return stock; }
    public Categorie getCategorie() { return categorie; }
    public void setPrix(BigDecimal prix) { this.prix = prix; }
    public void setStock(int stock) { this.stock = stock; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }
}
