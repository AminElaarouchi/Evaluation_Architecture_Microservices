package ma.projet.service;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ma.projet.classes.Femme;
import ma.projet.classes.Homme;
import ma.projet.classes.Mariage;
import ma.projet.dao.AbstractHibernateDao;
import org.hibernate.SessionFactory;

public class HommeService extends AbstractHibernateDao<Homme> {
    public HommeService(SessionFactory sessionFactory) { super(sessionFactory, Homme.class); }

    public List<Femme> epousesEntre(long hommeId, LocalDate debut, LocalDate fin) {
        return read(s -> s.createQuery("select distinct m.femme from Mariage m where m.homme.id = :id and m.dateDebut <= :fin and (m.dateFin is null or m.dateFin >= :debut) order by m.femme.nom", Femme.class)
                .setParameter("id", hommeId).setParameter("debut", debut).setParameter("fin", fin).getResultList());
    }

    public List<Mariage> mariagesAvecDetails(long hommeId) {
        return read(s -> s.createQuery("select m from Mariage m join fetch m.femme where m.homme.id = :id order by m.dateDebut", Mariage.class)
                .setParameter("id", hommeId).getResultList());
    }

    public List<Homme> mariesAQuatreFemmesEntre(LocalDate debut, LocalDate fin) {
        return read(s -> {
            CriteriaBuilder cb = s.getCriteriaBuilder();
            CriteriaQuery<Homme> query = cb.createQuery(Homme.class);
            Root<Mariage> mariage = query.from(Mariage.class);
            query.select(mariage.get("homme"))
                    .where(cb.between(mariage.get("dateDebut"), debut, fin))
                    .groupBy(mariage.get("homme"))
                    .having(cb.equal(cb.countDistinct(mariage.get("femme")), 4L));
            return s.createQuery(query).getResultList();
        });
    }
}
