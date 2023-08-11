package es.mueblesCastilla.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.mueblesCastilla.model.Compra;
@Repository
public interface ICompraRepository extends JpaRepository<Compra, Integer> {

}
