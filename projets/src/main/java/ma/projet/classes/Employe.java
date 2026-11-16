package ma.projet.classes;

import javax.persistence.*;

@Entity
@Table(name = "employes")
public class Employe {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false) private String nom;
    @Column(nullable = false) private String prenom;

    protected Employe() {}
    public Employe(String nom, String prenom) { this.nom = nom; this.prenom = prenom; }
    public Long getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
}
