package ma.projet.service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.dao.AbstractHibernateDao;
import org.hibernate.SessionFactory;

public class ProjetService extends AbstractHibernateDao<Projet> {
    public ProjetService(SessionFactory sessionFactory) { super(sessionFactory, Projet.class); }

    public List<Tache> tachesPlanifiees(long projetId) {
        return read(s -> s.createQuery("select t from Tache t where t.projet.id = :id order by t.debutPlanifie", Tache.class)
                .setParameter("id", projetId).getResultList());
    }

    public List<EmployeTache> tachesRealisees(long projetId) {
        return read(s -> s.createQuery("select et from EmployeTache et join fetch et.tache t where t.projet.id = :id order by et.debutReel", EmployeTache.class)
                .setParameter("id", projetId).getResultList());
    }

    public BigDecimal coutGlobal(long projetId) {
        return tachesPlanifiees(projetId).stream().map(Tache::getPrix)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public long joursRealises(long projetId) {
        return tachesRealisees(projetId).stream()
                .mapToLong(et -> ChronoUnit.DAYS.between(et.getDebutReel(), et.getFinReelle()) + 1)
                .sum();
    }
}
