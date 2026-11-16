package ma.projet.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import ma.projet.classes.LigneCommande;
import ma.projet.classes.Produit;
import ma.projet.dao.AbstractHibernateDao;
import org.hibernate.SessionFactory;

public class ProduitService extends AbstractHibernateDao<Produit> {
    public ProduitService(SessionFactory sessionFactory) { super(sessionFactory, Produit.class); }

    public List<Produit> parCategorie(long categorieId) {
        return read(s -> s.createQuery("select p from Produit p where p.categorie.id = :id order by p.reference", Produit.class)
                .setParameter("id", categorieId).getResultList());
    }

    public List<Produit> commandesEntre(LocalDate debut, LocalDate fin) {
        return read(s -> s.createQuery("select distinct l.produit from LigneCommande l where l.commande.dateCommande between :debut and :fin order by l.produit.reference", Produit.class)
                .setParameter("debut", debut).setParameter("fin", fin).getResultList());
    }

    public List<LigneCommande> dansCommande(long commandeId) {
        return read(s -> s.createQuery("select l from LigneCommande l join fetch l.produit where l.commande.id = :id order by l.produit.reference", LigneCommande.class)
                .setParameter("id", commandeId).getResultList());
    }

    public List<Produit> prixSuperieurA100() {
        return read(s -> s.createNamedQuery("Produit.prixSuperieurA", Produit.class)
                .setParameter("minimum", new BigDecimal("100.00")).getResultList());
    }
}
