package es.mueblesCastilla.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.mueblesCastilla.model.Producto;
import es.mueblesCastilla.service.ICompraService;
import es.mueblesCastilla.service.IDetalleCompraService;
import es.mueblesCastilla.service.IProductoService;
import es.mueblesCastilla.service.IUsuarioService;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IProductoService productoService; 
	
	@Autowired
	private ICompraService compras;
	
	@GetMapping("")
	public String home(Model model) {
		
		List<Producto> productos=productoService.findAll();
		model.addAttribute("productos", productos);
		return "administrador/home";
	}
	
	@GetMapping("/usuarios")
	public String usuarios (Model model) {
		
		model.addAttribute("usuarios", usuarioService.findAll());
		
		return "administrador/usuarios";
	}
	
	@GetMapping("compras")
	public String detallesCompra(Model model) {
	
		model.addAttribute("Compras", compras.findAll());
		return "administrador/compras";
	}

}
