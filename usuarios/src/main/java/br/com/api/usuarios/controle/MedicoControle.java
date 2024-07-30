package br.com.api.usuarios.controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.usuarios.modelo.MedicoModelo;
import br.com.api.usuarios.servico.MedicoServico;
import br.com.api.usuarios.modelo.RespostaModelo;
import br.com.api.usuarios.repositorio.MedicoRepositorio;

@RestController
@CrossOrigin(origins = "*")
public class MedicoControle {

    @Autowired
    private MedicoRepositorio medicoRepositorio;
    @Autowired
    private MedicoServico medicoServico;

    @SuppressWarnings("rawtypes")
    @GetMapping("/listarMedicos")
    public Iterable listarMedicos() {
        return medicoServico.listarMedicos();
    }

    @GetMapping("/testeMedico")
    public String rota() {
        return "rota funcionando";
    }

    @PutMapping("/alterarMedico")
    public ResponseEntity<?> alterarMedico(@RequestBody MedicoModelo mm) {
        return medicoServico.cadastrarAlterarMedico(mm, "alterar");
    }

    @PostMapping("/cadastrarMedico")
    public ResponseEntity<?> cadastrarMedico(@RequestBody MedicoModelo mm) {
        return medicoServico.cadastrarAlterarMedico(mm, "cadastrar");
    }


    @GetMapping("/medico/{id}")
public ResponseEntity<MedicoModelo> buscarMedicoPorId(@PathVariable Long id) {
    Optional<MedicoModelo> medicoOptional = medicoRepositorio.findById(id);
    if (medicoOptional.isPresent()) {
        return new ResponseEntity<>(medicoOptional.get(), HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}


    @DeleteMapping("/deletarMedico/{id}")
    public ResponseEntity<RespostaModelo> deletarMedico(@PathVariable Long id) {
        return medicoServico.deletarMedico(id);
    }
}
