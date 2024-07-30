package br.com.api.usuarios.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.usuarios.modelo.PacienteModelo;

@Repository
public interface PacienteRepositorio extends JpaRepository<PacienteModelo, Long> {
    PacienteModelo findByCpf(String cpf);
}
