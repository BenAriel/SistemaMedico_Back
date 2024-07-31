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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@CrossOrigin(origins = "*")
@Api(value = "Paciente Controle", description = "APIs para operações de pacientes")
public class PacienteControle {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private PacienteServico pacienteServico;

    @ApiOperation(value = "Listar todos os pacientes")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Lista de pacientes retornada com sucesso"),
        @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    @SuppressWarnings("rawtypes")
    @GetMapping("/listarPacientes")
    public Iterable listarPacientes() {
        return pacienteServico.listarPacientes();
    }

    @ApiOperation(value = "Alterar um paciente")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Paciente alterado com sucesso"),
        @ApiResponse(code = 400, message = "Erro na validação dos dados de entrada")
    })
    @PutMapping("/alterarPaciente")
    public ResponseEntity<?> alterarPaciente(@RequestBody PacienteModelo pm) {
        return pacienteServico.cadastrarAlterarPaciente(pm, "alterar");
    }

    @ApiOperation(value = "Cadastrar um novo paciente")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Paciente cadastrado com sucesso"),
        @ApiResponse(code = 400, message = "Erro na validação dos dados de entrada"),
        @ApiResponse(code = 409, message = "CPF já cadastrado")
    })
    @PostMapping("/cadastrarPaciente")
    public ResponseEntity<?> cadastrarPaciente(@RequestBody PacienteModelo pm) {
        return pacienteServico.cadastrarAlterarPaciente(pm, "cadastrar");
    }

    @ApiOperation(value = "Deletar um paciente pelo ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Paciente deletado com sucesso"),
        @ApiResponse(code = 404, message = "Paciente não encontrado")
    })
    @DeleteMapping("/deletarPaciente/{id}")
    public ResponseEntity<RespostaModelo> deletarPaciente(@PathVariable Long id) {
        return pacienteServico.deletarPaciente(id);
    }

    @ApiOperation(value = "Verificar se um CPF já está cadastrado")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "CPF verificado com sucesso"),
        @ApiResponse(code = 500, message = "Erro interno do servidor")
    })
    @GetMapping("/verificarCpf")
    public ResponseEntity<Boolean> verificarCpf(@RequestParam String cpf) {
        return pacienteServico.verificarCpf(cpf);
    }

    @ApiOperation(value = "Buscar um paciente pelo ID")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Paciente encontrado com sucesso"),
        @ApiResponse(code = 404, message = "Paciente não encontrado")
    })
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
