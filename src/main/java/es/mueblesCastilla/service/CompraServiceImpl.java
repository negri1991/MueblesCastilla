package es.mueblesCastilla.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.mueblesCastilla.model.Compra;
import es.mueblesCastilla.repository.ICompraRepository;

@Service
public class CompraServiceImpl implements ICompraService{
	
	@Autowired
	private ICompraRepository compraRepository;
	
	@Override
	public Compra save(Compra compra) {
		
		return compraRepository.save(compra);
	}

}
