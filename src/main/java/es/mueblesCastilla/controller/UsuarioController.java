package es.mueblesCastilla.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.mueblesCastilla.model.Compra;
import es.mueblesCastilla.model.Usuario;
import es.mueblesCastilla.service.ICompraService;
import es.mueblesCastilla.service.IUsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	private final Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);

	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private ICompraService compraService;
	
	BCryptPasswordEncoder passEncode = new BCryptPasswordEncoder();

	// usuario/registro
	@GetMapping("/registro")
	public String createUsuario() {

		return "usuario/registro";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("elUsuario") Usuario usuario, BindingResult validacionResultado) {

		usuario.setTipo("ADMIN");
		usuario.setPassword(passEncode.encode(usuario.getPassword()));
		usuarioService.save(usuario);

		return "redirect:/";

	}

	@GetMapping("/login")
	public String login() {
		return "usuario/login";
	}

	@PostMapping("/acceder")
	public String acceder(Usuario usuario, HttpSession session) {
		LOGGER.info("Accesos: {}", usuario);

		Optional<Usuario> user = usuarioService.findByEmail(usuario.getEmail());

		if (user.isPresent()) {
			session.setAttribute("idUsuario", user.get().getId());
			session.setAttribute("idNombre", user.get().getNombre());
			if (user.get().getTipo().equalsIgnoreCase("ADMIN")) {
				return "redirect:/administrador";
			} else {
				return "redirect:/";
			}
		} else {
			LOGGER.info("Usuario no existe");
		}
		return "redirect:/";
	}

	@GetMapping("/compras")
	public String getCompras(HttpSession sesion, Model model) {
		// session
		model.addAttribute("sesion", sesion.getAttribute("idUsuario"));
		model.addAttribute("sesionNombre", sesion.getAttribute("idNombre"));
		Usuario usuario= usuarioService.finById(Integer.parseInt( sesion.getAttribute("idUsuario").toString())).get();
		List<Compra> compras= compraService.findByUsuario(usuario);
		
		model.addAttribute("compras", compras);
		
		return "usuario/compras";
	}
	@GetMapping("/detalle/{id}")
	public String detalleCompra(@PathVariable Integer id, HttpSession session, Model model) {
		LOGGER.info("id de la compra: {}",id);
		Optional <Compra> compra=compraService.findById(id);
		model.addAttribute("detalles", compra.get().getDetalle());
		
		// session
		model.addAttribute("sesion", session.getAttribute("idUsuario"));
		model.addAttribute("sesionNombre", session.getAttribute("idNombre"));
		
		
				
		return "usuario/detallecompra";
	}
	
	@GetMapping("/cerrar")
	public String cerrarSesion(HttpSession session) {
		// cerrar session
		session.removeAttribute("idUsuario");
		
		return "redirect:/";
	}
	
}
