package repository;

public interface ISearch<T> {
    boolean searchByName(T obj, String name);
}
