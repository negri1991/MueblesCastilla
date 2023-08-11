package es.mueblesCastilla.service;

import java.util.ArrayList;
import java.util.List;

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

	@Override
	public List<Compra> findAll() {
		
		return compraRepository.findAll();
	}
	public String generarNumeroCompra() {
		int numero=0;
		String numeroConcatenado="";
		
		List<Compra> compras = findAll();
		
		List<Integer> numeros= new ArrayList<Integer>();
		
		compras.stream().forEach(o -> numeros.add(Integer.parseInt(o.getNumero())));
		
		if(compras.isEmpty()) {
			numero=1;
		}else {
			numero=numeros.stream().max(Integer::compare).get();
			numero++;
		}
		if(numero<10) {
			numeroConcatenado="000000000"+String.valueOf(numero);
		}else if(numero<100) {
			numeroConcatenado="00000000"+String.valueOf(numero);
		}else if(numero<1000) {
			numeroConcatenado="0000000"+String.valueOf(numero);
		}
		return numeroConcatenado;
	}

}
