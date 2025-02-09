package br.com.api.usuarios.controle;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import br.com.api.usuarios.modelo.RespostaModelo;
import br.com.api.usuarios.modelo.UsuarioModelo;
import br.com.api.usuarios.servico.UsuarioServico;

@RestController
@CrossOrigin(origins = "*")
public class UsuarioControle {

    @Autowired
    private UsuarioServico usuarioServico;
    @SuppressWarnings("rawtypes")
    @GetMapping("/listarusuarios")
    public Iterable listarUsuarios()
    {
        return usuarioServico.listarUsuarios();
    }
    @PostMapping("/cadastrarUsuario")
        public ResponseEntity<?> cadastrarAgendamento(@RequestBody UsuarioModelo um)
        {
            return usuarioServico.cadastrarUsuario(um);
        }
        @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody UsuarioModelo usuario) {
        return usuarioServico.loginUsuario(usuario.getEmail(), usuario.getSenha());
    }
    @DeleteMapping("/deletarUsuario/{id}")
    public ResponseEntity<RespostaModelo> deletarUsuario(@PathVariable Long id) {
        return usuarioServico.deletarUsuario(id);
    }
    @PostMapping("/alterarSenha")
public ResponseEntity<?> alterarSenha(@RequestBody Map<String, String> payload) {
    String email = payload.get("email");
    String novaSenha = payload.get("novaSenha");
    return usuarioServico.alterarSenha(email, novaSenha);
}

    @PostMapping("/recuperarSenha")
    public ResponseEntity<RespostaModelo> recuperarSenha(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        return usuarioServico.recuperarSenha(email);
    }
    @PostMapping("/verificarCodigo")
public ResponseEntity<RespostaModelo> verificarCodigo(@RequestBody Map<String, String> payload) {
    String email = payload.get("email");
    String codigo = payload.get("codigo");
    return usuarioServico.verificarCodigo(email, codigo);
}
    
}