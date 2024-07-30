package br.com.api.usuarios.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.usuarios.modelo.MedicoModelo;
import br.com.api.usuarios.modelo.RespostaModelo;
import br.com.api.usuarios.repositorio.MedicoRepositorio;

@Service
public class MedicoServico {

    @Autowired
    private MedicoRepositorio medicoRepositorio;

    @Autowired
    private RespostaModelo respostaModelo;

    public ResponseEntity<?> cadastrarAlterarMedico(MedicoModelo mm, String acao) {
        if (mm.getNomeCompleto() == null || mm.getNomeCompleto().isEmpty() || mm.getNomeCompleto().isBlank()) {
            respostaModelo.setMensagem("Nome completo é obrigatório");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else if (mm.getConselhoMedico() == null || mm.getConselhoMedico().isEmpty() || mm.getConselhoMedico().isBlank()) {
            respostaModelo.setMensagem("Conselho médico é obrigatório");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else if (mm.getUfConselho() == null || mm.getUfConselho().isEmpty() || mm.getUfConselho().isBlank()) {
            respostaModelo.setMensagem("UF do conselho é obrigatório");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else if (mm.getNumeroConselho() == null || mm.getNumeroConselho().isEmpty() || mm.getNumeroConselho().isBlank()) {
            respostaModelo.setMensagem("Número do conselho é obrigatório");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else if (mm.getCbo() == null || mm.getCbo().isEmpty() || mm.getCbo().isBlank()) {
            respostaModelo.setMensagem("CBO é obrigatório");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else if (mm.getCpf() == null || mm.getCpf().isEmpty() || mm.getCpf().isBlank()) {
            respostaModelo.setMensagem("CPF é obrigatório");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else if (mm.getLogradouro() == null || mm.getLogradouro().isEmpty() || mm.getLogradouro().isBlank()) {
            respostaModelo.setMensagem("Logradouro é obrigatório");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else if (mm.getBairro() == null || mm.getBairro().isEmpty() || mm.getBairro().isBlank()) {
            respostaModelo.setMensagem("Bairro é obrigatório");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else if (mm.getCidade() == null || mm.getCidade().isEmpty() || mm.getCidade().isBlank()) {
            respostaModelo.setMensagem("Cidade é obrigatória");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else if (mm.getUf() == null || mm.getUf().isEmpty() || mm.getUf().isBlank()) {
            respostaModelo.setMensagem("UF é obrigatória");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else if (mm.getCep() == null || mm.getCep().isEmpty() || mm.getCep().isBlank()) {
            respostaModelo.setMensagem("CEP é obrigatório");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else if (mm.getTelefone() == null || mm.getTelefone().isEmpty() || mm.getTelefone().isBlank()) {
            respostaModelo.setMensagem("Telefone é obrigatório");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else if (mm.getEmail() == null || mm.getEmail().isEmpty() || mm.getEmail().isBlank()) {
            respostaModelo.setMensagem("Email é obrigatório");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        }

        else if (medicoRepositorio.findByNumeroConselho(mm.getNumeroConselho()) != null && acao.equals("cadastrar")) {
            respostaModelo.setMensagem("número conselho já cadastrado");
            return new ResponseEntity<>(respostaModelo, HttpStatus.CONFLICT);
            }

        if (acao.equals("alterar")) {
            return new ResponseEntity<>(medicoRepositorio.save(mm), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(medicoRepositorio.save(mm), HttpStatus.CREATED);
        }
    }

    public Iterable<MedicoModelo> listarMedicos() {
        return medicoRepositorio.findAll();
    }

    public ResponseEntity<RespostaModelo> deletarMedico(Long id) {
        if (medicoRepositorio.existsById(id)) {
            medicoRepositorio.deleteById(id);
            respostaModelo.setMensagem("Médico deletado com sucesso");
            return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.OK);
        } else {
            respostaModelo.setMensagem("Médico não encontrado");
            return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.NOT_FOUND);
        }
    }
}
