package ma.projet.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import ma.projet.classes.Femme;
import ma.projet.dao.AbstractHibernateDao;
import org.hibernate.SessionFactory;

public class FemmeService extends AbstractHibernateDao<Femme> {
    public FemmeService(SessionFactory sessionFactory) { super(sessionFactory, Femme.class); }

    public Optional<Femme> plusAgee() {
        return read(s -> s.createQuery("from Femme f order by f.dateNaissance asc", Femme.class)
                .setMaxResults(1).getResultStream().findFirst());
    }

    public long nombreEnfantsEntre(long femmeId, LocalDate debut, LocalDate fin) {
        return read(s -> ((Number) s.createNamedQuery("Femme.nombreEnfantsEntre")
                .setParameter("femmeId", femmeId)
                .setParameter("debut", java.sql.Date.valueOf(debut))
                .setParameter("fin", java.sql.Date.valueOf(fin))
                .getSingleResult()).longValue());
    }

    public List<Femme> marieesAuMoinsDeuxFois() {
        return read(s -> s.createNamedQuery("Femme.marieesDeuxFois", Femme.class).getResultList());
    }
}
