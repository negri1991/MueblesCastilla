package es.mueblesCastilla.service;

import java.util.List;
import java.util.Optional;

import es.mueblesCastilla.model.Usuario;

public interface IUsuarioService {
	Optional<Usuario> finById(Integer id);
	Usuario save (Usuario usuario);
	Optional<Usuario> findByEmail(String email);
	List <Usuario> findAll();

}
