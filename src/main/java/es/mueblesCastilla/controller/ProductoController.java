package es.mueblesCastilla.controller;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import es.mueblesCastilla.model.Producto;
import es.mueblesCastilla.model.Usuario;
import es.mueblesCastilla.service.IProductoService;
import es.mueblesCastilla.service.UploadFileService;

@Controller
@RequestMapping("/productos")
public class ProductoController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	private IProductoService productoService;
	
	@Autowired
	private UploadFileService upload;
	
	@GetMapping("")
	public String show(Model model) {
		model.addAttribute("productos", productoService.findAll());
		return "productos/show.html";
	}
	@GetMapping("/create")
	public String create() {
		return "productos/create.html";
	}
	
	@PostMapping("/save")
	public String save(Producto producto,@RequestParam("img") MultipartFile file) throws IOException {//RequestParam coge el nombre del campo  nombre. 
		LOGGER.info("Este es el objeto producto {}",producto);
		
		Usuario u= new Usuario(1,"", "", "", "", "", "", "");
		producto.setUsuario(u);
		
		//imagen
		if (producto.getId()==null){//Cuando se crea un producto
			String nombreImagen = upload.saveImage(file);
			producto.setImagen(nombreImagen);
		} else {
			
		}
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
	public String update(Producto producto, @RequestParam("img") MultipartFile file) throws IOException {
		
		Producto p= new Producto();
		p=productoService.get(producto.getId()).get();
		
		
		if(file.isEmpty()) {//editamos el producto pero no cambiamos la imagen

			producto.setImagen(p.getImagen());
		}else {//Cuando se edita la imagen
			

			
			//Editar cuando no sea la imagen por defecto.
			if(!p.getImagen().equals("default.jpg")) {
				upload.deleteImage(p.getImagen());
			}
			
			String nombreImagen = upload.saveImage(file);
			producto.setImagen(nombreImagen);
		}
		producto.setUsuario(p.getUsuario());
		productoService.update(producto);
		return "redirect:/productos";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id) {
		
		Producto p = new Producto();
			p=productoService.get(id).get();
			
			//Eliminar cuando no sea la imagen por defecto.
			if(!p.getImagen().equals("default.jpg")) {
				upload.deleteImage(p.getImagen());
			}
		
		
		
		productoService.delete(id);
		return "redirect:/productos";
	}
	
}
