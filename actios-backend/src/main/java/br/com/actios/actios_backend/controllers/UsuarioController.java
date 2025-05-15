package br.com.actios.actios_backend.controllers;

import br.com.actios.actios_backend.model.Usuario;
import br.com.actios.actios_backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // POST: Cadastrar novo usuário
    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) throws Exception {
        Usuario novoUsuario = usuarioService.cadastrar(usuario);
        return ResponseEntity.ok(novoUsuario);
    }

    // GET: Listar todos os usuários
    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    // GET: Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Integer id) throws Exception {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    // PUT: Atualizar usuário
    @PutMapping("/atualizar")
    public ResponseEntity<Usuario> atualizarUsuario(@RequestBody Usuario usuario) throws Exception {
        Usuario atualizado = usuarioService.atualizar(usuario);
        return ResponseEntity.ok(atualizado);
    }

    // DELETE: Excluir usuário
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Integer id) throws Exception {
        usuarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    // POST: Autenticar usuário
    @PostMapping("/login")
    public ResponseEntity<Usuario> autenticar(@RequestParam String email, @RequestParam String senha) throws Exception {
        Usuario usuario = usuarioService.autenticar(email, senha);
        return ResponseEntity.ok(usuario);
    }
}
