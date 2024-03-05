package tn.esprit.crud.services;
import java.util.List;
public interface IEVENT <T>{
    void addPst(T t);
    void delete(T t) throws Exception;
    void update(T t);
    List<T>readAll();
    T readByid(int id);
}
