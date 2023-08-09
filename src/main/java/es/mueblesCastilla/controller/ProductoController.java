package es.mueblesCastilla.controller;

import java.util.Optional;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.mueblesCastilla.model.Producto;
import es.mueblesCastilla.model.Usuario;
import es.mueblesCastilla.service.ProductoService;

@Controller
@RequestMapping("/productos")
public class ProductoController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	private ProductoService productoService;
	@GetMapping("")
	public String show(Model model) {
		model.addAttribute("productos", productoService.findAll());
		return "productos/show";
	}
	@GetMapping("/create")
	public String create() {
		return "productos/create.html";
	}
	
	@PostMapping("/save")
	public String save(Producto producto) {
		LOGGER.info("Este es el objeto producto {}",producto);
		
		Usuario u= new Usuario(1,"", "", "", "", "", "", "");
		producto.setUsuario(u);
		productoService.save(producto);
		return "redirect:/productos";
	}
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		Producto producto = new Producto();
		Optional<Producto> optionalProducto=productoService.get(id);
		producto = optionalProducto.get();
		
		LOGGER.info("Producto encontrado: {}", producto);
		model.addAttribute("producto", producto);
		
		return "productos/edit.html";
	}
	@PostMapping("/update")
	public String update(Producto producto) {
		productoService.update(producto);
		return "redirect:/productos";
	}
	
}
