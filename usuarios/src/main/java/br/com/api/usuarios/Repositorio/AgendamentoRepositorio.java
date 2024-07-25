package br.com.api.usuarios.Repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.api.usuarios.modelo.AgendamentoModelo;

@Repository
public interface AgendamentoRepositorio  extends CrudRepository<AgendamentoModelo, Long> {
    
}