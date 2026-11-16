package ma.projet.service;

import ma.projet.classes.Mariage;
import ma.projet.dao.AbstractHibernateDao;
import org.hibernate.SessionFactory;

public class MariageService extends AbstractHibernateDao<Mariage> {
    public MariageService(SessionFactory sessionFactory) { super(sessionFactory, Mariage.class); }
}
