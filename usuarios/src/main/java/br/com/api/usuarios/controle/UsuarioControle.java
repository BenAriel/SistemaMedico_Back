package br.com.api.usuarios.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.usuarios.servico.UsuarioServico;

@RestController
public class UsuarioControle {

    @Autowired
    private UsuarioServico usuarioServico;
    @SuppressWarnings("rawtypes")
    @GetMapping("/listarusuarios")
    public Iterable listarUsuarios()
    {
        return usuarioServico.listarUsuarios();
    }
}
