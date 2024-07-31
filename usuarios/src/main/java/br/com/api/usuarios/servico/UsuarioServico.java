package br.com.api.usuarios.servico;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.api.usuarios.modelo.RespostaModelo;
import br.com.api.usuarios.modelo.UsuarioModelo;
import br.com.api.usuarios.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServico {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioServico.class);

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private EmailServico emailServico;

    @Autowired
    private RespostaModelo respostaModelo;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseEntity<?> cadastrarUsuario(UsuarioModelo um) {
        logger.info("Recebendo dados do usuário: {}", um);

        if (um.getNome() == null || um.getNome().isEmpty()) {
            respostaModelo.setMensagem("Nome é obrigatório");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else if (um.getEmail() == null || um.getEmail().isEmpty()) {
            respostaModelo.setMensagem("Email é obrigatório");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else if (um.getSenha() == null || um.getSenha().isEmpty()) {
            respostaModelo.setMensagem("Senha é obrigatória");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        } else if (usuarioRepositorio.findByEmail(um.getEmail()) != null) {
            respostaModelo.setMensagem("Email já cadastrado");
            return new ResponseEntity<>(respostaModelo, HttpStatus.CONFLICT);
        } else {
            String encodedPassword = passwordEncoder.encode(um.getSenha());
            um.setSenha(encodedPassword);
            UsuarioModelo salvo = usuarioRepositorio.save(um);
            logger.info("Usuário salvo com sucesso: {}", salvo);
            return new ResponseEntity<>(salvo, HttpStatus.CREATED);
        }
    }

    public ResponseEntity<?> loginUsuario(String email, String senha) {
        UsuarioModelo usuario = usuarioRepositorio.findByEmail(email);
        if (usuario != null && passwordEncoder.matches(senha, usuario.getSenha())) {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } else {
            respostaModelo.setMensagem("Email ou senha inválidos");
            return new ResponseEntity<>(respostaModelo, HttpStatus.UNAUTHORIZED);
        }
    }
    public ResponseEntity<?> alterarSenha(String email, String novaSenha) {
        UsuarioModelo usuario = usuarioRepositorio.findByEmail(email);
        
        if (usuario == null) {
            respostaModelo.setMensagem("Usuário não encontrado");
            return new ResponseEntity<>(respostaModelo, HttpStatus.NOT_FOUND);
        }
        
        if (novaSenha == null || novaSenha.isEmpty()) {
            respostaModelo.setMensagem("Nova senha é obrigatória");
            return new ResponseEntity<>(respostaModelo, HttpStatus.BAD_REQUEST);
        }
        
        
        String encodedPassword = passwordEncoder.encode(novaSenha);
        usuario.setSenha(encodedPassword);
        
        usuarioRepositorio.save(usuario);
        respostaModelo.setMensagem("Senha alterada com sucesso");
        return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
    }

    public ResponseEntity<RespostaModelo> deletarUsuario(Long id) {
        if (usuarioRepositorio.existsById(id)) {
            usuarioRepositorio.deleteById(id);
            respostaModelo.setMensagem("usuario deletado com sucesso");
            return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.OK);
        } else {
            respostaModelo.setMensagem("usuario não encontrado");
            return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<RespostaModelo> recuperarSenha(String email) {
        UsuarioModelo usuario = usuarioRepositorio.findByEmail(email);
        if (usuario != null) {
            int passwordCode = (new Random()).nextInt(9999);

            emailServico.sendEmail(email, "Recuperação de senha", "Seu código é " + passwordCode);

            usuario.setSenhaRecuperacao(String.valueOf(passwordCode));
            System.out.println("Senha de recuperação: " + passwordCode);
            usuarioRepositorio.save(usuario);
            respostaModelo.setMensagem("Email enviado com sucesso");
            return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.OK);
        } else {
            System.out.println("Email não encontrado");
            respostaModelo.setMensagem("Email não encontrado");
            return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.NOT_FOUND);
        }
    }

    public Iterable<UsuarioModelo> listarUsuarios() {
        return usuarioRepositorio.findAll();
    }

    public ResponseEntity<RespostaModelo> verificarCodigo(String email, String codigo) {
        
        UsuarioModelo usuario = usuarioRepositorio.findByEmail(email);
        
        
        if (usuario != null && codigo.equals(usuario.getSenhaRecuperacao())) {
            usuario.setSenhaRecuperacao(null);
            usuarioRepositorio.save(usuario);
            
            respostaModelo.setMensagem("Código verificado com sucesso");
            return new ResponseEntity<>(respostaModelo, HttpStatus.OK);
        } else {
            respostaModelo.setMensagem("Código inválido ou usuário não encontrado");
            return new ResponseEntity<>(respostaModelo, HttpStatus.UNAUTHORIZED);
        }
    }
    
}

