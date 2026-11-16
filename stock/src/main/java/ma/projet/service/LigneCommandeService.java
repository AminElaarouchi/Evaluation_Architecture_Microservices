package ma.projet.service;

import ma.projet.classes.LigneCommande;
import ma.projet.dao.AbstractHibernateDao;
import org.hibernate.SessionFactory;

public class LigneCommandeService extends AbstractHibernateDao<LigneCommande> {
    public LigneCommandeService(SessionFactory sessionFactory) { super(sessionFactory, LigneCommande.class); }

    @Override
    public LigneCommande create(LigneCommande ligne) {
        return write(session -> {
            var produit = session.get(ma.projet.classes.Produit.class, ligne.getProduit().getId());
            if (produit == null || produit.getStock() < ligne.getQuantite()) {
                throw new IllegalStateException("Stock insuffisant");
            }
            produit.setStock(produit.getStock() - ligne.getQuantite());
            session.persist(ligne);
            return ligne;
        });
    }

    @Override
    public LigneCommande update(LigneCommande ligne) {
        return write(session -> {
            LigneCommande ancienne = session.get(LigneCommande.class, ligne.getId());
            if (ancienne == null) throw new IllegalArgumentException("Ligne introuvable");
            int difference = ligne.getQuantite() - ancienne.getQuantite();
            var produit = ancienne.getProduit();
            if (difference > produit.getStock()) throw new IllegalStateException("Stock insuffisant");
            produit.setStock(produit.getStock() - difference);
            ancienne.setQuantite(ligne.getQuantite());
            return ancienne;
        });
    }

    @Override
    public boolean deleteById(long id) {
        return write(session -> {
            LigneCommande ligne = session.get(LigneCommande.class, id);
            if (ligne == null) return false;
            var produit = ligne.getProduit();
            produit.setStock(produit.getStock() + ligne.getQuantite());
            session.remove(ligne);
            return true;
        });
    }
}
