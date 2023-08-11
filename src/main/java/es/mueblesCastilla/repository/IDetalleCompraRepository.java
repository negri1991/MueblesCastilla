package es.mueblesCastilla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.mueblesCastilla.model.DetalleCompra;

@Repository
public interface IDetalleCompraRepository extends JpaRepository<DetalleCompra, Integer>{
	

}
