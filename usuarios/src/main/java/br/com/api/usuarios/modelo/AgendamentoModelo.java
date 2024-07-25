package br.com.api.usuarios.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "agendamentos")
public class AgendamentoModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private MedicoModelo medico;
    
    @ManyToOne
    private PacienteModelo paciente;
    
    private String motivoConsulta;
    private String dataConsulta;
    private String horaConsulta;
    private String localConsulta;
    private String observacoes;
}
