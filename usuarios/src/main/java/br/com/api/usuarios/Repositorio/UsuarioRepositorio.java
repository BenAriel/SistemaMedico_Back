package br.com.api.usuarios.Repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.api.usuarios.modelo.UsuarioModelo;

@Repository
public interface UsuarioRepositorio  extends CrudRepository<UsuarioModelo, Long> {
    UsuarioModelo findByEmailAndSenha(String email, String senha);
}
