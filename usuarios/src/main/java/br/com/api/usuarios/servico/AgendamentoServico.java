package br.com.api.usuarios.servico;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import br.com.api.usuarios.excecao.ResourceNotFoundException;
import br.com.api.usuarios.modelo.AgendamentoModelo;
import br.com.api.usuarios.modelo.MedicoModelo;
import br.com.api.usuarios.modelo.PacienteModelo;
import br.com.api.usuarios.modelo.RespostaModelo;
import br.com.api.usuarios.repositorio.AgendamentoRepositorio;
import br.com.api.usuarios.repositorio.MedicoRepositorio;
import br.com.api.usuarios.repositorio.PacienteRepositorio;

@Service
public class AgendamentoServico {

    @Autowired
    private AgendamentoRepositorio agendamentoRepositorio;

    @Autowired
    private MedicoRepositorio medicoRepositorio;

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private RespostaModelo respostaModelo;

    public ResponseEntity<?> cadastrarAlterarAgendamento(Map<String, Object> agendamentoMap, String acao) {
        try {
            Long medicoId = Long.parseLong(agendamentoMap.get("medico").toString());
            Long pacienteId = Long.parseLong(agendamentoMap.get("paciente").toString());
    
            MedicoModelo medico = medicoRepositorio.findById(medicoId)
                    .orElseThrow(() -> new ResourceNotFoundException("Medico not found"));
            PacienteModelo paciente = pacienteRepositorio.findById(pacienteId)
                    .orElseThrow(() -> new ResourceNotFoundException("Paciente not found"));
    
            AgendamentoModelo agendamento;
    
            if (acao.equals("alterar")) {
                Long agendamentoId = Long.parseLong(agendamentoMap.get("id").toString());
                agendamento = agendamentoRepositorio.findById(agendamentoId)
                        .orElseThrow(() -> new ResourceNotFoundException("Agendamento not found"));
            } else {
                agendamento = new AgendamentoModelo();
            }
    
            agendamento.setMedico(medico);
            agendamento.setPaciente(paciente);
            agendamento.setMotivoConsulta(agendamentoMap.get("motivoConsulta").toString());
            agendamento.setDataConsulta(agendamentoMap.get("dataConsulta").toString());
            agendamento.setHoraConsulta(agendamentoMap.get("horaConsulta").toString());
            agendamento.setLocalConsulta(agendamentoMap.get("localConsulta").toString());
            agendamento.setObservacoes(agendamentoMap.get("observacoes").toString());
    
            return new ResponseEntity<AgendamentoModelo>(agendamentoRepositorio.save(agendamento), HttpStatus.OK);
    
        } catch (Exception e) {
            respostaModelo.setMensagem(e.getMessage());
            return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
        }
    }

    public Iterable<AgendamentoModelo> listarAgendamentos() {
        return agendamentoRepositorio.findAll();
    }

    public ResponseEntity<RespostaModelo> deletarAgendamento(Long id) {
        if (agendamentoRepositorio.existsById(id)) {
            agendamentoRepositorio.deleteById(id);
            respostaModelo.setMensagem("Agendamento deletado com sucesso");
            return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.OK);
        } else {
            respostaModelo.setMensagem("Agendamento não encontrado");
            return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.NOT_FOUND);
        }
    }

    public boolean isHorarioDisponivel(AgendamentoModelo agendamento) {
        List<AgendamentoModelo> agendamentos = agendamentoRepositorio.findByMedicoAndDataConsulta(agendamento.getMedico(), agendamento.getDataConsulta());
        LocalTime horaConsulta = LocalTime.parse(agendamento.getHoraConsulta());

        for (AgendamentoModelo existente : agendamentos) {
            LocalTime horaExistente = LocalTime.parse(existente.getHoraConsulta());
            if (horaConsulta.equals(horaExistente)) {
                return false;
            }
        }
        return true;
    }

    public List<LocalTime> getHorariosDisponiveis(Long medicoId, String dataConsulta) {
        List<AgendamentoModelo> agendamentos = agendamentoRepositorio.findByMedicoAndDataConsulta(
                medicoRepositorio.findById(medicoId).orElseThrow(() -> new ResourceNotFoundException("Medico not found")),
                dataConsulta);

        List<LocalTime> horariosDisponiveis = new ArrayList<>();
        for (int hour = 8; hour <= 17; hour++) {
            horariosDisponiveis.add(LocalTime.of(hour, 0));
        }

        for (AgendamentoModelo agendamento : agendamentos) {
            LocalTime horaConsulta = LocalTime.parse(agendamento.getHoraConsulta());
            horariosDisponiveis.remove(horaConsulta);
        }

        return horariosDisponiveis;
    }
}
