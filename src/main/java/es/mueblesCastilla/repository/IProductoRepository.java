package es.mueblesCastilla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.mueblesCastilla.model.Producto;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, Integer>{
	
	

}
