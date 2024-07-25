package br.com.api.usuarios.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.usuarios.modelo.AgendamentoModelo;
import br.com.api.usuarios.modelo.RespostaModelo;
import br.com.api.usuarios.servico.AgendamentoServico;

@RestController
@CrossOrigin(origins = "*")
public class AgendamentoControle {

    @Autowired
    private AgendamentoServico agendamentoServico;
    @SuppressWarnings("rawtypes")
    @GetMapping("/listarAgendamentos")
    public Iterable listarAgendamento()
    {
        return agendamentoServico.listarAgendamentos();
    }
    @GetMapping("/teste")
    public String rota()
    {
        return "rota funcionando";
    }


    @PutMapping ("/alterarAgendamento")
    public ResponseEntity<?> alterarAgendamento(@RequestBody AgendamentoModelo am)
    {
        return agendamentoServico.cadastrarAlterarAgendamento(am, "alterar");
    }

    @PostMapping("/cadastrarAgendamento")
        public ResponseEntity<?> cadastrarAgendamento(@RequestBody AgendamentoModelo am)
        {
            return agendamentoServico.cadastrarAlterarAgendamento(am, "cadastrar");
        }

    @DeleteMapping("/deletarAgendamento/{id}")
    public ResponseEntity<RespostaModelo> deletarAgendamento(@PathVariable Long id)
    {
        return agendamentoServico.deletarAgendamento(id);
    } 
}
