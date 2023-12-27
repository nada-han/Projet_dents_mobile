package ma.ensa.backProject.dao;

import java.util.List;

public interface IDao <T>{
    T create(T o);
    T update(T o);
    T findById(Long id);
    List<T> findAll();
    boolean delete(T o);
}
