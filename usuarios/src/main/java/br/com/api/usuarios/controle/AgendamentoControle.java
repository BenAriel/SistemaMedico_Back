package br.com.api.usuarios.controle;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.usuarios.modelo.AgendamentoModelo;
import br.com.api.usuarios.modelo.RespostaModelo;
import br.com.api.usuarios.repositorio.AgendamentoRepositorio;
import br.com.api.usuarios.servico.AgendamentoServico;

@RestController
@CrossOrigin(origins = "*")
public class AgendamentoControle {

    @Autowired
    private AgendamentoRepositorio agendamentoRepositorio;
    @Autowired
    private AgendamentoServico agendamentoServico;

    @GetMapping("/listarAgendamentos")
    public Iterable<AgendamentoModelo> listarAgendamento() {
        return agendamentoServico.listarAgendamentos();
    }

    @PutMapping("/alterarAgendamento")
    public ResponseEntity<?> alterarAgendamento(@RequestBody Map<String, Object> agendamentoMap) {
        return agendamentoServico.cadastrarAlterarAgendamento(agendamentoMap, "alterar");
    }

    @PostMapping("/cadastrarAgendamento")
    public ResponseEntity<?> cadastrarAgendamento(@RequestBody Map<String, Object> agendamentoMap) {
        return agendamentoServico.cadastrarAlterarAgendamento(agendamentoMap, "cadastrar");
    }

    @DeleteMapping("/deletarAgendamento/{id}")
public ResponseEntity<RespostaModelo> deletarAgendamento(@PathVariable Long id) {
    return agendamentoServico.deletarAgendamento(id);
}



  
    @GetMapping("/agendamento/{id}")
    public ResponseEntity<AgendamentoModelo> buscarAgendamentoPorId(@PathVariable Long id) {
        Optional<AgendamentoModelo> agendamentoOptional = agendamentoRepositorio.findById(id);
        if (agendamentoOptional.isPresent()) {
            return new ResponseEntity<>(agendamentoOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/horariosDisponiveis")
    public ResponseEntity<List<LocalTime>> getHorariosDisponiveis(
            @RequestParam Long medicoId,
            @RequestParam String dataConsulta) {
        try {
            List<LocalTime> horariosDisponiveis = agendamentoServico.getHorariosDisponiveis(medicoId, dataConsulta);
            return new ResponseEntity<>(horariosDisponiveis, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}