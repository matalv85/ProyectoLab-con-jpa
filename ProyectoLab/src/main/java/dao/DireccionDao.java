package dao;

import java.util.Collection;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import modelos.Cuenta;
import modelos.Direccion;

public interface DireccionDao extends Dao<Direccion>{
	
	public void save(Direccion direccion);

	public Direccion read(Long id);

	public void update(Direccion direccion);

	public void borrar(Direccion direccion);

	public Collection<Direccion> readAll();

}
