package es.mueblesCastilla.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import es.mueblesCastilla.model.Usuario;
import es.mueblesCastilla.service.ICompraService;
import es.mueblesCastilla.service.IDetalleCompraService;
import es.mueblesCastilla.service.IUsuarioService;
import jakarta.servlet.http.HttpSession;
import es.mueblesCastilla.service.IProductoService;


@Controller
@RequestMapping("/")
public class HomeController {
	
	private final Logger log= LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private IProductoService productoService; 
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private ICompraService compraService;
	
	@Autowired
	private IDetalleCompraService detalleCompraService;

	
	//Para almacenar los detalles del pedido.
	List<DetalleCompra> detalles=new ArrayList<DetalleCompra>();

	Compra compra = new Compra();
	@GetMapping("")
	public String home(Model model, HttpSession session) {
		log.info("sesion del usuario: {}", session.getAttribute("idUsuario"));
		model.addAttribute("productos", productoService.findAll());

		
		//session
		model.addAttribute("sesion", session.getAttribute("idUsuario"));
		model.addAttribute("sesionNombre", session.getAttribute("idNombre"));
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
		
		//Validar que el producto no se añada 2 veces
		
		Integer idProducto= producto.getId();
		boolean ingresado=detalles.stream().anyMatch(p -> p.getProducto().getId()==idProducto);
		
		if(!ingresado) {
			detalles.add(detalleCompra);
		}else {
			model.addAttribute("productoAgregado", true);

		}
		
		
		sumaTotal=detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
		compra.setTotal(sumaTotal);
		model.addAttribute("cart", detalles);
		model.addAttribute("Compra", compra);
		
		return "usuario/carrito";
		
	}
	
	//QUitar un producto del carrito
	@GetMapping("/delete/cart/{id}")
	public String deleteProductoCart(@PathVariable Integer id, Model model) {
		
		List<DetalleCompra> comprasNueva = new ArrayList<DetalleCompra>();
		
		for(DetalleCompra detalleCompra: detalles) {
			if(detalleCompra.getProducto().getId()!=id) {
				comprasNueva.add(detalleCompra);
				
			}
		}
		//poner la nueva lista con los productos restantes
		detalles=comprasNueva;
		double sumaTotal=0;
		
		sumaTotal=detalles.stream().mapToDouble(dt->dt.getTotal()).sum();
		compra.setTotal(sumaTotal);
		model.addAttribute("cart", detalles);
		model.addAttribute("Compra", compra);
		
		return"usuario/carrito";
	}
	
	@GetMapping("/getCart")
	public String getCart(Model model, HttpSession session) {
		//session
		model.addAttribute("sesion", session.getAttribute("idUsuario"));
		model.addAttribute("sesionNombre", session.getAttribute("idNombre"));
		
		
		model.addAttribute("cart", detalles);
		model.addAttribute("Compra", compra);
		
	
		return "/usuario/carrito";
	}
	@GetMapping("/factura")
	public String factura(Model model, HttpSession session){

		Usuario usuario = usuarioService.finById(Integer.parseInt(session.getAttribute("idUsuario").toString())).get();

		model.addAttribute("cart", detalles);
		model.addAttribute("Compra", compra);
		model.addAttribute("usuario",usuario);
		if(detalles.isEmpty()) {			
			return "redirect:/home2";
			
		}else {
		return "usuario/resumenFactura";
		}
	}
	
	//Guardar la compra
	@GetMapping("/saveCompra")
	public String saveCompra( HttpSession session){
	
		
		Date fechaCreacion = new Date();
		compra.setFechaCreacion(fechaCreacion);
		compra.setNumero(compraService.generarNumeroCompra());
		
		//usuario
		Usuario usuario = usuarioService.finById(Integer.parseInt(session.getAttribute("idUsuario").toString())).get();
		compra.setUsuario(usuario);
		compraService.save(compra);
		
		//guardar detalles
		for (DetalleCompra dt:detalles) {
			dt.setCompra(compra);
			detalleCompraService.save(dt);
		}
		//limpiar
		compra =new Compra();
		detalles.clear();
		
		
		
		return "redirect:/";
	}
	@PostMapping("/search")
	public String searchProducto(@RequestParam String nombre, Model model) {
		log.info("Nombre del producto: {}", nombre);
		//nombre=nombre.toLowerCase();
		List<Producto> productos = productoService.findAll().stream().filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()) || p.getNombre().toUpperCase().contains(nombre.toUpperCase())).collect(Collectors.toList());
		List<Producto> productos2 = productoService.findAll().stream().filter(p -> p.getDescripcion().toLowerCase().contains(nombre.toLowerCase()) || p.getDescripcion().toUpperCase().contains(nombre.toUpperCase())).collect(Collectors.toList());
		productos.addAll(productos2);
		model.addAttribute("productos", productos);
		
		return "/usuario/home";
	}
	@GetMapping("/home2")
	public String home2(Model model, HttpSession session) {
		//carrito vacio
		boolean carritoVacio;
		carritoVacio=true;
		model.addAttribute("carritoVacio", carritoVacio);
		
		//Session
		model.addAttribute("sesion", session.getAttribute("idUsuario"));
		model.addAttribute("sesionNombre", session.getAttribute("idNombre"));

		//Productos
		model.addAttribute("productos", productoService.findAll());
			return "usuario/home2";

	}
}