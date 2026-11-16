package ma.projet.service;

import java.util.List;
import ma.projet.classes.Employe;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.dao.AbstractHibernateDao;
import org.hibernate.SessionFactory;

public class EmployeService extends AbstractHibernateDao<Employe> {
    public EmployeService(SessionFactory sessionFactory) { super(sessionFactory, Employe.class); }

    public List<Tache> tachesRealisees(long employeId) {
        return read(s -> s.createQuery("select distinct et.tache from EmployeTache et where et.employe.id = :id order by et.tache.nom", Tache.class)
                .setParameter("id", employeId).getResultList());
    }

    public List<Projet> projetsGeres(long employeId) {
        return read(s -> s.createQuery("select p from Projet p where p.gestionnaire.id = :id order by p.nom", Projet.class)
                .setParameter("id", employeId).getResultList());
    }
}
