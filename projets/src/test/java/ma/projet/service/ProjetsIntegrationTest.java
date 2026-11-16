package ma.projet.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Properties;
import ma.projet.classes.*;
import ma.projet.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

class ProjetsIntegrationTest {
    @Test
    void tempsReelEtCoutsDesProjets() {
        Properties config = new Properties();
        config.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        config.setProperty("hibernate.connection.url", "jdbc:h2:mem:projets;DB_CLOSE_DELAY=-1");
        config.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        config.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        try (SessionFactory factory = HibernateUtil.createSessionFactory(config,
                Employe.class, Projet.class, Tache.class, EmployeTache.class)) {
            EmployeService employes = new EmployeService(factory);
            ProjetService projets = new ProjetService(factory);
            TacheService taches = new TacheService(factory);
            EmployeTacheService realisations = new EmployeTacheService(factory);

            Employe chef = employes.create(new Employe("Safi", "Said"));
            Employe developpeuse = employes.create(new Employe("Rami", "Amal"));
            Projet stock = projets.create(new Projet("Gestion de stock", LocalDate.of(2013, 1, 14), chef));
            Tache analyse = taches.create(new Tache("Analyse", new BigDecimal("1200.00"),
                    LocalDate.of(2013, 2, 1), LocalDate.of(2013, 2, 20), stock));
            Tache conception = taches.create(new Tache("Conception", new BigDecimal("900.00"),
                    LocalDate.of(2013, 3, 1), LocalDate.of(2013, 3, 15), stock));
            realisations.create(new EmployeTache(developpeuse, analyse,
                    LocalDate.of(2013, 2, 10), LocalDate.of(2013, 2, 20)));
            realisations.create(new EmployeTache(chef, conception,
                    LocalDate.of(2013, 3, 10), LocalDate.of(2013, 3, 15)));

            assertEquals(2, projets.tachesPlanifiees(stock.getId()).size());
            assertEquals(2, projets.tachesRealisees(stock.getId()).size());
            assertEquals(new BigDecimal("2100.00"), projets.coutGlobal(stock.getId()));
            assertEquals(17, projets.joursRealises(stock.getId()));
            assertEquals(LocalDate.of(2013, 2, 10), projets.tachesRealisees(stock.getId()).get(0).getDebutReel());
            assertEquals(1, employes.tachesRealisees(developpeuse.getId()).size());
            assertEquals(1, employes.projetsGeres(chef.getId()).size());
            assertEquals(1, taches.prixSuperieurA1000().size());
            assertEquals(1, taches.realiseesEntre(LocalDate.of(2013, 2, 1), LocalDate.of(2013, 2, 28)).size());
            assertEquals(2, employes.findAll().size());
            assertEquals(2, realisations.findAll().size());
            assertTrue(taches.findById(analyse.getId()).isPresent());
        }
    }
}
