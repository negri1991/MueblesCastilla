package es.mueblesCastilla.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.mueblesCastilla.model.Usuario;
import es.mueblesCastilla.repository.IUsuarioRepository;

@Service
public class UsuarioServiceImpl implements IUsuarioService{

	@Autowired
	private IUsuarioRepository usuarioRepository;

	@Override
	public Optional<Usuario> finById(Integer id) {
		// TODO Auto-generated method stub
		return usuarioRepository.findById(id);
	}
	

}
