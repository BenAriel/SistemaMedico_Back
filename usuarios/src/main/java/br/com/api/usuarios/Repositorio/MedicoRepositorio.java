package br.com.api.usuarios.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.usuarios.modelo.MedicoModelo;

@Repository
public interface MedicoRepositorio  extends JpaRepository<MedicoModelo, Long> {
    MedicoModelo findByNumeroConselho(String numeroConselho);
}