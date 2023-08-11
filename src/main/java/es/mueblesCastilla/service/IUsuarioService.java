package es.mueblesCastilla.service;

import java.util.Optional;

import es.mueblesCastilla.model.Usuario;

public interface IUsuarioService {
	Optional<Usuario> finById(Integer id);

}
