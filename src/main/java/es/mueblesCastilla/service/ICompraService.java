package es.mueblesCastilla.service;

import java.util.List;

import es.mueblesCastilla.model.Compra;

public interface ICompraService {

	public Compra save(Compra compra);

	public List<Compra> findAll();
	
	public String generarNumeroCompra();
		
}
