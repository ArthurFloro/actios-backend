package br.com.actios.actios_backend.controllers;

import br.com.actios.actios_backend.dto.LoginDTO;
import br.com.actios.actios_backend.dto.UsuarioDTO;
import br.com.actios.actios_backend.model.Faculdade;
import br.com.actios.actios_backend.model.Usuario;
import br.com.actios.actios_backend.repositorys.FaculdadeRepository;
import br.com.actios.actios_backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para operações relacionadas a usuários.
 * <p>
 * Mapeado para a rota base "/api/usuarios" e fornece endpoints para
 * gerenciamento de usuários, incluindo cadastro, autenticação e CRUD completo.
 *
 * <p>Versão 1.1 - Melhorias no cadastro para suportar faculdade opcional
 *
 * @author Equipe Actios
 * @version 1.1
 * @since 2023-08-30
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final FaculdadeRepository faculdadeRepository;

    /**
     * Construtor com injeção de dependência do serviço de usuários.
     *
     * @param usuarioService Serviço contendo a lógica de negócios para usuários
     * @param faculdadeRepository Repositório para operações com faculdades
     */
    @Autowired
    public UsuarioController(UsuarioService usuarioService, FaculdadeRepository faculdadeRepository) {
        this.usuarioService = usuarioService;
        this.faculdadeRepository = faculdadeRepository;
    }

    /**
     * Cadastra um novo usuário no sistema.
     * <p>
     * Aceita vínculo opcional com faculdade através do campo faculdadeId.
     *
     * @param usuarioDTO DTO contendo os dados para cadastro
     * @return ResponseEntity com o UsuarioDTO do usuário cadastrado
     * @throws Exception Se ocorrer erro durante o cadastro (ex: email já existente)
     */
    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioDTO> criarUsuario(@RequestBody UsuarioDTO usuarioDTO) throws Exception {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setCurso(usuarioDTO.getCurso());

        // Vincula à faculdade apenas se o ID for fornecido
        if(usuarioDTO.getFaculdadeId() != null) {
            Faculdade faculdade = faculdadeRepository.findById(usuarioDTO.getFaculdadeId())
                    .orElseThrow(() -> new Exception("Faculdade não encontrada"));
            usuario.setFaculdade(faculdade);
        }

        Usuario novoUsuario = usuarioService.cadastrar(usuario);
        return ResponseEntity.ok(UsuarioDTO.fromUsuario(novoUsuario));
    }

    /**
     * Lista todos os usuários cadastrados no formato DTO.
     *
     * @return ResponseEntity contendo lista de UsuarioDTO
     */
    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        List<UsuarioDTO> usuariosDTO = usuarios.stream()
                .map(UsuarioDTO::fromUsuario)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuariosDTO);
    }

    /**
     * Busca um usuário específico pelo ID.
     *
     * @param id ID do usuário a ser buscado
     * @return ResponseEntity com o UsuarioDTO do usuário encontrado
     * @throws Exception Se o usuário não for encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Integer id) throws Exception {
        Usuario usuario = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioDTO.fromUsuario(usuario));
    }

    /**
     * Atualiza os dados de um usuário existente.
     *
     * @param usuarioDTO DTO com os dados atualizados
     * @return ResponseEntity com o UsuarioDTO atualizado
     * @throws Exception Se o usuário não existir ou ocorrer erro na atualização
     */
    @PutMapping("/atualizar")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@RequestBody UsuarioDTO usuarioDTO) throws Exception {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(usuarioDTO.getIdUsuario());
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setCurso(usuarioDTO.getCurso());

        if(usuarioDTO.getFaculdadeId() != null) {
            Faculdade faculdade = faculdadeRepository.findById(usuarioDTO.getFaculdadeId())
                    .orElseThrow(() -> new Exception("Faculdade não encontrada"));
            usuario.setFaculdade(faculdade);
        }

        Usuario atualizado = usuarioService.atualizar(usuario);
        return ResponseEntity.ok(UsuarioDTO.fromUsuario(atualizado));
    }

    /**
     * Exclui um usuário pelo ID.
     *
     * @param id ID do usuário a ser excluído
     * @return ResponseEntity vazio com status 204 (No Content)
     * @throws Exception Se o usuário não existir ou ocorrer erro na exclusão
     */
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Integer id) throws Exception {
        usuarioService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Autentica um usuário com email e senha.
     *
     * @param loginDTO DTO contendo credenciais de login (email e senha)
     * @return ResponseEntity com UsuarioDTO do usuário autenticado
     */
    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> autenticar(@RequestBody LoginDTO loginDTO) {
        Usuario usuario = usuarioService.autenticar(loginDTO.getEmail(), loginDTO.getSenha());
        return ResponseEntity.ok(UsuarioDTO.fromUsuario(usuario));
    }
}