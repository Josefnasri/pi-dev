package interfaces;

import java.sql.SQLException;
import java.util.List;

public interface IProduit <T>{
    void add(T t) throws SQLException;
    void addPst(T t) throws SQLException;
    void delete(T t);
    void update(T t);
    List<T> readAll();
    T readById(int id);
}
