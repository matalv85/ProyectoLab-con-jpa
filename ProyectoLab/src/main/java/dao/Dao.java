package dao;

import java.util.Collection;

public interface Dao<T> {
	void save(T objeto);
	T read(Long id);
	void update(T t);
	void borrar(T t);
	Collection<T> readAll();
}

