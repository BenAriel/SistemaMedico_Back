package br.com.api.usuarios.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.usuarios.Repositorio.UsuarioRepositorio;
import br.com.api.usuarios.modelo.RespostaModelo;
import br.com.api.usuarios.modelo.UsuarioModelo;

@Service
public class UsuarioServico {
    //listar todos os usuários. Esse método equivale ao select * from usuarios.
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;


    @Autowired
    private RespostaModelo respostaModelo;
    //metodo para cadastrar

    public ResponseEntity<?> cadastrarUsuario(UsuarioModelo um) {
        if(um.getNome() == null || um.getNome().isEmpty() || um.getNome().isBlank() || um.getNome().equals(""))
        {
            respostaModelo.setMensagem("Nome é obrigatório");
            return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
        }
        else if(um.getEmail() == null || um.getEmail().isEmpty() || um.getEmail().isBlank() || um.getEmail().equals(""))
        {
            respostaModelo.setMensagem("Email é obrigatório");
            return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
        }
        else if(um.getSenha() == null || um.getSenha().isEmpty() || um.getSenha().isBlank() || um.getSenha().equals(""))
        {
            respostaModelo.setMensagem("Senha é obrigatório");
            return new ResponseEntity<RespostaModelo>(respostaModelo, HttpStatus.BAD_REQUEST);
        }
        else
        {
            return new ResponseEntity<UsuarioModelo>(usuarioRepositorio.save(um), HttpStatus.CREATED);
        }
    }

    public Iterable<UsuarioModelo> listarUsuarios() {
        return usuarioRepositorio.findAll();
    }
    
}
