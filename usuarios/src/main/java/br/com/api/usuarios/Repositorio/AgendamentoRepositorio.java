package br.com.api.usuarios.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.usuarios.modelo.AgendamentoModelo;
import br.com.api.usuarios.modelo.MedicoModelo;

@Repository
public interface AgendamentoRepositorio  extends JpaRepository<AgendamentoModelo, Long> {
     List<AgendamentoModelo> findByMedicoAndDataConsulta(MedicoModelo medico, String dataConsulta);
    
}