package br.com.api.usuarios.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import br.com.api.usuarios.modelo.PacienteModelo;
import br.com.api.usuarios.modelo.RespostaModelo;
import br.com.api.usuarios.repositorio.PacienteRepositorio;

@Service
public class PacienteServico {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private RespostaModelo respostaModelo;

    
    public ResponseEntity<Boolean> verificarCpf(String cpf) {
        PacienteModelo paciente = pacienteRepositorio.findByCpf(cpf);
        if (paciente != null) {
            
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> cadastrarAlterarPaciente(PacienteModelo pm, String acao) {
        if (pm.getNomeCompleto() == null || pm.getNomeCompleto().isEmpty() || pm.getNomeCompleto().isBlank()) {
            respostaModelo.setMensagem("Nome completo é obrigatório");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else if (pm.getCpf() == null || pm.getCpf().isEmpty() || pm.getCpf().isBlank()) {
            respostaModelo.setMensagem("CPF é obrigatório");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else if (pm.getDataNascimento() == null || pm.getDataNascimento().isEmpty() || pm.getDataNascimento().isBlank()) {
            respostaModelo.setMensagem("Data de nascimento é obrigatória");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else if (pm.getSexo() == null || pm.getSexo().isEmpty() || pm.getSexo().isBlank()) {
            respostaModelo.setMensagem("Sexo é obrigatório");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else if (pm.getRg() == null || pm.getRg().isEmpty() || pm.getRg().isBlank()) {
            respostaModelo.setMensagem("RG é obrigatório");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else if (pm.getOrgaoEmissor() == null || pm.getOrgaoEmissor().isEmpty() || pm.getOrgaoEmissor().isBlank()) {
            respostaModelo.setMensagem("Órgão emissor é obrigatório");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else if (pm.getLogradouro() == null || pm.getLogradouro().isEmpty() || pm.getLogradouro().isBlank()) {
            respostaModelo.setMensagem("Logradouro é obrigatório");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else if (pm.getBairro() == null || pm.getBairro().isEmpty() || pm.getBairro().isBlank()) {
            respostaModelo.setMensagem("Bairro é obrigatório");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else if (pm.getCidade() == null || pm.getCidade().isEmpty() || pm.getCidade().isBlank()) {
            respostaModelo.setMensagem("Cidade é obrigatória");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else if (pm.getUf() == null || pm.getUf().isEmpty() || pm.getUf().isBlank()) {
            respostaModelo.setMensagem("UF é obrigatória");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else if (pm.getCep() == null || pm.getCep().isEmpty() || pm.getCep().isBlank()) {
            respostaModelo.setMensagem("CEP é obrigatório");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else if (pm.getTelefone() == null || pm.getTelefone().isEmpty() || pm.getTelefone().isBlank()) {
            respostaModelo.setMensagem("Telefone é obrigatório");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else if (pm.getEmail() == null || pm.getEmail().isEmpty() || pm.getEmail().isBlank()) {
            respostaModelo.setMensagem("Email é obrigatório");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else if (pm.getObservacoes() == null || pm.getObservacoes().isEmpty() || pm.getObservacoes().isBlank()) {
            respostaModelo.setMensagem("Observações é obrigatório");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        }
        else if (pacienteRepositorio.findByCpf(pm.getCpf()) != null && acao.equals("cadastrar")) {
        respostaModelo.setMensagem("CPF já cadastrado");
        return new ResponseEntity<>(respostaModelo, HttpStatus.CONFLICT);
        }

        if (acao.equals("alterar")) {
            return new ResponseEntity<>(pacienteRepositorio.save(pm), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(pacienteRepositorio.save(pm), HttpStatus.CREATED);
        }
    }

    public Iterable<PacienteModelo> listarPacientes() {
        return pacienteRepositorio.findAll();
    }

    public ResponseEntity<RespostaModelo> deletarPaciente(Long id) {
        if (pacienteRepositorio.existsById(id)) {
            pacienteRepositorio.deleteById(id);
            respostaModelo.setMensagem("Paciente deletado com sucesso");
            return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.OK);
        } else {
            respostaModelo.setMensagem("Paciente não encontrado");
            return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.NOT_FOUND);
        }
    }
}
