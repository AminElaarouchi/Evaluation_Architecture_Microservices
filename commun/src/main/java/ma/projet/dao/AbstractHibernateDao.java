package ma.projet.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public abstract class AbstractHibernateDao<T> implements IDao<T> {
    protected final SessionFactory sessionFactory;
    private final Class<T> entityClass;

    protected AbstractHibernateDao(SessionFactory sessionFactory, Class<T> entityClass) {
        this.sessionFactory = sessionFactory;
        this.entityClass = entityClass;
    }

    protected <R> R read(Function<Session, R> action) {
        try (Session session = sessionFactory.openSession()) {
            return action.apply(session);
        }
    }

    protected <R> R write(Function<Session, R> action) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                R result = action.apply(session);
                transaction.commit();
                return result;
            } catch (RuntimeException e) {
                if (transaction.isActive()) transaction.rollback();
                throw e;
            }
        }
    }

    @Override
    public T create(T entity) {
        return write(session -> { session.persist(entity); return entity; });
    }

    @Override
    public T update(T entity) {
        return write(session -> { @SuppressWarnings("unchecked") T merged = (T) session.merge(entity); return merged; });
    }

    @Override
    public boolean deleteById(long id) {
        return write(session -> {
            T entity = session.get(entityClass, id);
            if (entity == null) return false;
            session.remove(entity);
            return true;
        });
    }

    @Override
    public Optional<T> findById(long id) {
        return read(session -> Optional.ofNullable(session.get(entityClass, id)));
    }

    @Override
    public List<T> findAll() {
        return read(session -> session.createQuery("from " + entityClass.getSimpleName(), entityClass).getResultList());
    }
}
