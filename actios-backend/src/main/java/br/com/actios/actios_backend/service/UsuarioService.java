package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.model.Faculdade;
import br.com.actios.actios_backend.model.Usuario;
import br.com.actios.actios_backend.repositorys.FaculdadeRepository;
import br.com.actios.actios_backend.repositorys.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final FaculdadeRepository faculdadeRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, FaculdadeRepository faculdadeRepository) {
        this.usuarioRepository = usuarioRepository;
        this.faculdadeRepository = faculdadeRepository;
    }

    public Usuario cadastrar(Usuario usuario) throws Exception {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new Exception("E-mail já cadastrado.");
        }

        // Se vier ID de faculdade, buscar e associar
        if (usuario.getFaculdade() != null && usuario.getFaculdade().getIdFaculdade() != null) {
            Faculdade faculdade = faculdadeRepository.findById(usuario.getFaculdade().getIdFaculdade())
                    .orElseThrow(() -> new Exception("Faculdade não encontrada."));
            usuario.setFaculdade(faculdade);
        }

        usuario.setDataCadastro(LocalDateTime.now());
        return usuarioRepository.save(usuario);
    }

    public Usuario autenticar(String email, String senha) throws Exception {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmailAndSenha(email, senha);
        return usuarioOpt.orElseThrow(() -> new Exception("Credenciais inválidas."));
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Integer id) throws Exception {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new Exception("Usuário não encontrado."));
    }

    public Usuario atualizar(Usuario usuario) throws Exception {
        if (!usuarioRepository.existsById(usuario.getIdUsuario())) {
            throw new Exception("Usuário não encontrado.");
        }

        // Atualizar faculdade, se fornecida
        if (usuario.getFaculdade() != null && usuario.getFaculdade().getIdFaculdade() != null) {
            Faculdade faculdade = faculdadeRepository.findById(usuario.getFaculdade().getIdFaculdade())
                    .orElseThrow(() -> new Exception("Faculdade não encontrada."));
            usuario.setFaculdade(faculdade);
        }

        return usuarioRepository.save(usuario);
    }

    public void excluir(Integer id) throws Exception {
        if (!usuarioRepository.existsById(id)) {
            throw new Exception("Usuário não encontrado.");
        }
        usuarioRepository.deleteById(id);
    }
}
