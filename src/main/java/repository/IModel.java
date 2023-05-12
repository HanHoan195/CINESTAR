package repository;

public interface IModel<T> {
    long getId();


    void update (T obj);
    T parseData(String line) throws InstantiationException, IllegalAccessException;
}
