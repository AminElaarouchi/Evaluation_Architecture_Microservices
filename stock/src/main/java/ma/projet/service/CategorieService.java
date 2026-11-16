package ma.projet.service;

import ma.projet.classes.Categorie;
import ma.projet.dao.AbstractHibernateDao;
import org.hibernate.SessionFactory;

public class CategorieService extends AbstractHibernateDao<Categorie> {
    public CategorieService(SessionFactory sessionFactory) { super(sessionFactory, Categorie.class); }
}
