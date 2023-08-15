package es.mueblesCastilla.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import es.mueblesCastilla.model.Usuario;
import es.mueblesCastilla.service.IUsuarioService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	private final Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);

	@Autowired
	private IUsuarioService usuarioService;

	// usuario/registro
	@GetMapping("/registro")
	public String createUsuario() {
		
		return "usuario/registro";
	}

	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("elUsuario") Usuario usuario, BindingResult validacionResultado) {
		
				usuario.setTipo("USER");
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
		
		Optional<Usuario> user=usuarioService.findByEmail(usuario.getEmail());
		
		if(user.isPresent()) {
			session.setAttribute("idUsuario", user.get().getId());
			session.setAttribute("idNombre", user.get().getNombre());
			if (user.get().getTipo().equalsIgnoreCase("ADMIN")) {
				return "redirect:/administrador";
			}else {
			return "redirect:/";
			}
		}else {
			LOGGER.info("Usuario no existe");
			}
		return "redirect:/";
	}
}
