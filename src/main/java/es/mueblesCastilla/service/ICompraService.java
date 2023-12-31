package es.mueblesCastilla.service;

import java.util.List;
import java.util.Optional;

import es.mueblesCastilla.model.Compra;
import es.mueblesCastilla.model.Usuario;

public interface ICompraService {

	public Compra save(Compra compra);

	public List<Compra> findAll();
	
	public String generarNumeroCompra();
	
	List<Compra> findByUsuario (Usuario usuario);
	
	Optional<Compra> findById(Integer id);
}
