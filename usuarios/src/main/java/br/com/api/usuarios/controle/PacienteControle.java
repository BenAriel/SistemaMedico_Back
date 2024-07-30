package br.com.api.usuarios.controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.api.usuarios.modelo.PacienteModelo;
import br.com.api.usuarios.modelo.RespostaModelo;
import br.com.api.usuarios.repositorio.PacienteRepositorio;
import br.com.api.usuarios.servico.PacienteServico;

@RestController
@CrossOrigin(origins = "*")
public class PacienteControle {

    
    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private PacienteServico pacienteServico;

    @SuppressWarnings("rawtypes")
    @GetMapping("/listarPacientes")
    public Iterable listarPacientes() {
        return pacienteServico.listarPacientes();
    }

    @PutMapping("/alterarPaciente")
    public ResponseEntity<?> alterarPaciente(@RequestBody PacienteModelo pm) {
        return pacienteServico.cadastrarAlterarPaciente(pm, "alterar");
    }

    @PostMapping("/cadastrarPaciente")
    public ResponseEntity<?> cadastrarPaciente(@RequestBody PacienteModelo pm) {
        return pacienteServico.cadastrarAlterarPaciente(pm, "cadastrar");
    }

    @DeleteMapping("/deletarPaciente/{id}")
    public ResponseEntity<RespostaModelo> deletarPaciente(@PathVariable Long id) {
        return pacienteServico.deletarPaciente(id);
    }

    @GetMapping("/verificarCpf")
    public ResponseEntity<Boolean> verificarCpf(@RequestParam String cpf) {
        return pacienteServico.verificarCpf(cpf);
    }

    @GetMapping("/paciente/{id}")
    public ResponseEntity<PacienteModelo> buscarPacientePorId(@PathVariable Long id) {
        Optional<PacienteModelo> pacienteOptional = pacienteRepositorio.findById(id);
        if (pacienteOptional.isPresent()) {
            return new ResponseEntity<>(pacienteOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
