package ma.projet.service;

import ma.projet.classes.EmployeTache;
import ma.projet.dao.AbstractHibernateDao;
import org.hibernate.SessionFactory;

public class EmployeTacheService extends AbstractHibernateDao<EmployeTache> {
    public EmployeTacheService(SessionFactory sessionFactory) { super(sessionFactory, EmployeTache.class); }
}
