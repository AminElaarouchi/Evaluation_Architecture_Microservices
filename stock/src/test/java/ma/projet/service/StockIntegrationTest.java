package ma.projet.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Properties;
import ma.projet.classes.*;
import ma.projet.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

class StockIntegrationTest {
    @Test
    void crudEtRequetesDuStock() {
        Properties config = new Properties();
        config.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        config.setProperty("hibernate.connection.url", "jdbc:h2:mem:stock;DB_CLOSE_DELAY=-1");
        config.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        config.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        try (SessionFactory factory = HibernateUtil.createSessionFactory(config,
                Categorie.class, Produit.class, Commande.class, LigneCommande.class)) {
            CategorieService categories = new CategorieService(factory);
            ProduitService produits = new ProduitService(factory);
            CommandeService commandes = new CommandeService(factory);
            LigneCommandeService lignes = new LigneCommandeService(factory);

            Categorie pc = categories.create(new Categorie("PC", "Ordinateurs"));
            Categorie accessoires = categories.create(new Categorie("ACC", "Accessoires"));
            Produit ordinateur = produits.create(new Produit("ES12", new BigDecimal("120.00"), 10, pc));
            Produit souris = produits.create(new Produit("ZR85", new BigDecimal("80.00"), 20, accessoires));
            Commande mars = commandes.create(new Commande(LocalDate.of(2013, 3, 14)));
            Commande avril = commandes.create(new Commande(LocalDate.of(2013, 4, 5)));
            LigneCommande lignePc = lignes.create(new LigneCommande(mars, ordinateur, 7));
            lignes.create(new LigneCommande(avril, souris, 2));

            assertEquals(1, produits.parCategorie(pc.getId()).size());
            assertEquals("ES12", produits.commandesEntre(LocalDate.of(2013, 3, 1), LocalDate.of(2013, 3, 31)).get(0).getReference());
            assertEquals(7, produits.dansCommande(mars.getId()).get(0).getQuantite());
            assertEquals(1, produits.prixSuperieurA100().size());
            assertEquals(3, produits.findById(ordinateur.getId()).orElseThrow().getStock());
            assertThrows(IllegalStateException.class, () -> lignes.create(new LigneCommande(avril, ordinateur, 4)));
            lignePc.setQuantite(8);
            lignes.update(lignePc);
            assertEquals(2, produits.findById(ordinateur.getId()).orElseThrow().getStock());
            assertEquals(2, categories.findAll().size());
            assertEquals(2, commandes.findAll().size());
            souris.setPrix(new BigDecimal("90.00"));
            produits.update(souris);
            assertEquals(new BigDecimal("90.00"), produits.findById(souris.getId()).orElseThrow().getPrix());
            assertTrue(lignes.deleteById(lignePc.getId()));
            assertEquals(10, produits.findById(ordinateur.getId()).orElseThrow().getStock());
        }
    }
}
