package ma.projet.service;

import ma.projet.classes.Commande;
import ma.projet.dao.AbstractHibernateDao;
import org.hibernate.SessionFactory;

public class CommandeService extends AbstractHibernateDao<Commande> {
    public CommandeService(SessionFactory sessionFactory) { super(sessionFactory, Commande.class); }
}
