package dao;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import modelos.Movimiento;

@Repository
public interface MovimientoDao extends Dao<Movimiento> {

	public void save(Movimiento movimiento);

	public Movimiento read(Long id);

	public void update(Movimiento movimiento);

	public void borrar(Movimiento movimiento);

	public Collection<Movimiento> readAll();

	public Collection<Movimiento> readAllById(long id);

}