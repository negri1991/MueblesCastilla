package es.mueblesCastilla.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.mueblesCastilla.model.Compra;
import es.mueblesCastilla.model.DetalleCompra;
import es.mueblesCastilla.model.Producto;
import es.mueblesCastilla.service.ProductoService;


@Controller
@RequestMapping("/")
public class HomeController {
	
	private final Logger log= LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private ProductoService productoService; 
	
	//Para almacenar los detalles del pedido.
	List<DetalleCompra> detalles=new ArrayList<DetalleCompra>();

	Compra compra = new Compra();
	@GetMapping("")
	public String home(Model model) {
		model.addAttribute("productos", productoService.findAll());
		
		return "usuario/home";
	}
	
	@GetMapping("productohome/{id}")
	public String productoHome(@PathVariable Integer id, Model model) {
		log.info("Id enviado como parámetro {}", id);
		Producto producto= new Producto();
		Optional<Producto> productoOptional = productoService.get(id);
		producto = productoOptional.get();
		
		model.addAttribute("producto", producto);
		return "usuario/productohome";
	}
	@PostMapping("/cart")
	public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {

		DetalleCompra detalleCompra = new DetalleCompra();
		Producto producto = new Producto();
		double sumaTotal = 0;
		Optional<Producto> optionalProducto = productoService.get(id);
		log.info("Producto añadido: {}", optionalProducto.get());
		log.info("Cantidad: {}", cantidad);
		producto=optionalProducto.get();
		
		detalleCompra.setCantidad(cantidad);
		detalleCompra.setPrecio(producto.getPrecio());
		detalleCompra.setNombre(producto.getNombre());
		detalleCompra.setTotal(producto.getPrecio()*cantidad);
		detalleCompra.setProducto(producto);
		detalles.add(detalleCompra);
		
		sumaTotal=detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
		compra.setTotal(sumaTotal);
		model.addAttribute("cart", detalles);
		model.addAttribute("Compra", compra);
		
		return "usuario/carrito";
		
	}

}
