package service;

import java.util.List;

public interface IService <T>{
    void addPst(T t);
    void delete(T t);
    void update(T t);
    List<T>readAll();
    T readByid(int id);
}
