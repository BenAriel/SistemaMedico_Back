package br.com.api.usuarios.servico;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.usuarios.Repositorio.UsuarioRepositorio;
import br.com.api.usuarios.modelo.RespostaModelo;
import br.com.api.usuarios.modelo.UsuarioModelo;

@Service
public class UsuarioServico {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioServico.class);

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private RespostaModelo respostaModelo;

    // método para cadastrar
    public ResponseEntity<?> cadastrarUsuario(UsuarioModelo um) {
        logger.info("Recebendo dados do usuário: {}", um);

        if (um.getNome() == null || um.getNome().isEmpty()) {
            respostaModelo.setMensagem("Nome é obrigatório");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else if (um.getEmail() == null || um.getEmail().isEmpty()) {
            respostaModelo.setMensagem("Email é obrigatório");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else if (um.getSenha() == null || um.getSenha().isEmpty()) {
            respostaModelo.setMensagem("Senha é obrigatório");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else {
            UsuarioModelo salvo = usuarioRepositorio.save(um);
            logger.info("Usuário salvo com sucesso: {}", salvo);
            return new ResponseEntity<>(salvo, HttpStatus.CREATED);
        }
    }

    public ResponseEntity<?> loginUsuario(String email, String senha) {
        UsuarioModelo usuario = usuarioRepositorio.findByEmailAndSenha(email, senha);
        if (usuario != null) {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } else {
            respostaModelo.setMensagem("Email ou senha inválidos");
            return new ResponseEntity<>(respostaModelo, HttpStatus.UNAUTHORIZED);
        }
    }

    public Iterable<UsuarioModelo> listarUsuarios() {
        return usuarioRepositorio.findAll();
    }
}
