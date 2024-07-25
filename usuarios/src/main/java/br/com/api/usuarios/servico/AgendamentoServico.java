package br.com.api.usuarios.servico;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.usuarios.Repositorio.AgendamentoRepositorio;
import br.com.api.usuarios.modelo.AgendamentoModelo;
import br.com.api.usuarios.modelo.RespostaModelo;

@Service
public class AgendamentoServico {
    //listar todos os usuários. Esse método equivale ao select * from usuarios.
    @Autowired
    private AgendamentoRepositorio agendamentoRepositorio;


    @Autowired
    private RespostaModelo respostaModelo;
    //metodo para cadastrar/alterar

    public ResponseEntity<?> cadastrarAlterarAgendamento(AgendamentoModelo am,String acao) {
        if(am.getDataConsulta() == null || am.getDataConsulta().isEmpty() || am.getDataConsulta().isBlank() || am.getDataConsulta().equals(""))
        {
            respostaModelo.setMensagem("Data é obrigatório");
            return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
        }
        else if(am.getHoraConsulta() == null || am.getHoraConsulta().isEmpty() || am.getHoraConsulta().isBlank() || am.getHoraConsulta().equals(""))
        {
            respostaModelo.setMensagem("Hora é obrigatório");
            return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
        }
        else if(am.getLocalConsulta() == null || am.getLocalConsulta().isEmpty() || am.getLocalConsulta().isBlank() || am.getLocalConsulta().equals(""))
        {
            respostaModelo.setMensagem("Local é obrigatório");
            return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
        }
        else if(am.getMotivoConsulta()== null || am.getMotivoConsulta().isEmpty() || am.getMotivoConsulta().isBlank() || am.getMotivoConsulta().equals(""))
        {
            respostaModelo.setMensagem("Motivo é obrigatório");
            return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
        }
        else if( am.getObservacoes() == null|| am.getObservacoes().isEmpty() || am.getObservacoes().isBlank() || am.getObservacoes().equals(""))
        {
            respostaModelo.setMensagem("Observações é obrigatório");
            return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
        }
        if(acao.equals("alterar"))
        {
            return new ResponseEntity<AgendamentoModelo>(agendamentoRepositorio.save(am), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<AgendamentoModelo>(agendamentoRepositorio.save(am), HttpStatus.CREATED);
        }
    }

    public Iterable<AgendamentoModelo> listarAgendamentos() {
        return agendamentoRepositorio.findAll();
    }

    public ResponseEntity<RespostaModelo> deletarAgendamento(Long id) {
        if(agendamentoRepositorio.existsById(id))
        {
            agendamentoRepositorio.deleteById(id);
            respostaModelo.setMensagem("Agendamento deletado com sucesso");
            return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.OK);
        }
        else
        {
            respostaModelo.setMensagem("Agendamento não encontrado");
            return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.NOT_FOUND);
        }
    }
    
}
