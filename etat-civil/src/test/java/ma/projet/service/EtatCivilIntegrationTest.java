package ma.projet.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import ma.projet.classes.*;
import ma.projet.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

class EtatCivilIntegrationTest {
    @Test
    void mariagesEtRequetesDemandees() {
        Properties config = new Properties();
        config.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        config.setProperty("hibernate.connection.url", "jdbc:h2:mem:etat_civil;DB_CLOSE_DELAY=-1");
        config.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        config.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        try (SessionFactory factory = HibernateUtil.createSessionFactory(config,
                Homme.class, Femme.class, Mariage.class)) {
            HommeService hommes = new HommeService(factory);
            FemmeService femmes = new FemmeService(factory);
            MariageService mariages = new MariageService(factory);

            List<Homme> hommesCrees = new ArrayList<>();
            for (int i = 0; i < 5; i++) hommesCrees.add(hommes.create(
                    new Homme("Homme" + i, "Prenom" + i, LocalDate.of(1970 + i, 1, 1))));
            List<Femme> femmesCreees = new ArrayList<>();
            for (int i = 0; i < 10; i++) femmesCreees.add(femmes.create(
                    new Femme("Femme" + i, "Prenom" + i, LocalDate.of(1960 + i, 1, 1))));
            Homme said = hommesCrees.get(0);
            for (int i = 0; i < 4; i++) mariages.create(new Mariage(said, femmesCreees.get(i),
                    LocalDate.of(1990 + i, 9, 3), null, i + 1));
            mariages.create(new Mariage(hommesCrees.get(1), femmesCreees.get(0),
                    LocalDate.of(1989, 1, 1), LocalDate.of(1989, 12, 31), 2));

            assertEquals(10, femmes.findAll().size());
            assertEquals(5, hommes.findAll().size());
            assertEquals(femmesCreees.get(0).getId(), femmes.plusAgee().orElseThrow().getId());
            assertEquals(4, hommes.epousesEntre(said.getId(), LocalDate.of(1990, 1, 1), LocalDate.of(2000, 1, 1)).size());
            assertEquals(1, femmes.nombreEnfantsEntre(femmesCreees.get(0).getId(),
                    LocalDate.of(1990, 1, 1), LocalDate.of(1990, 12, 31)));
            assertEquals(1, femmes.marieesAuMoinsDeuxFois().size());
            assertEquals(1, hommes.mariesAQuatreFemmesEntre(LocalDate.of(1990, 1, 1), LocalDate.of(2000, 1, 1)).size());
            assertEquals(4, hommes.mariagesAvecDetails(said.getId()).size());
            assertEquals(5, mariages.findAll().size());
        }
    }
}
