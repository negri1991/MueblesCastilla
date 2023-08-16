package es.mueblesCastilla.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.mueblesCastilla.model.Compra;
import es.mueblesCastilla.model.Usuario;
@Repository
public interface ICompraRepository extends JpaRepository<Compra, Integer> {
	
	List<Compra> findByUsuario (Usuario usuario);
}
