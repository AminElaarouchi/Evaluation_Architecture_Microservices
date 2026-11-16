package ma.projet.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Tache;
import ma.projet.dao.AbstractHibernateDao;
import org.hibernate.SessionFactory;

public class TacheService extends AbstractHibernateDao<Tache> {
    public TacheService(SessionFactory sessionFactory) { super(sessionFactory, Tache.class); }

    public List<Tache> prixSuperieurA1000() {
        return read(s -> s.createNamedQuery("Tache.prixSuperieurA", Tache.class)
                .setParameter("minimum", new BigDecimal("1000.00")).getResultList());
    }

    public List<EmployeTache> realiseesEntre(LocalDate debut, LocalDate fin) {
        return read(s -> s.createQuery("select et from EmployeTache et join fetch et.tache where et.debutReel >= :debut and et.finReelle <= :fin order by et.debutReel", EmployeTache.class)
                .setParameter("debut", debut).setParameter("fin", fin).getResultList());
    }
}
