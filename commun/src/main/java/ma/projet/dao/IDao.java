package ma.projet.dao;

import java.util.List;
import java.util.Optional;

public interface IDao<T> {
    T create(T entity);
    T update(T entity);
    boolean deleteById(long id);
    Optional<T> findById(long id);
    List<T> findAll();
}
