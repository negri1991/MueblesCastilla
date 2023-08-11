package es.mueblesCastilla.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.mueblesCastilla.model.DetalleCompra;
import es.mueblesCastilla.repository.IDetalleCompraRepository;

@Service
public class DetalleCompraServiceImpl implements IDetalleCompraService {

	@Autowired
	private IDetalleCompraRepository detalleCompraRepository;
	
	@Override
	public DetalleCompra save(DetalleCompra detalleCompra) {
		
		return detalleCompraRepository.save(detalleCompra);
	}

}
