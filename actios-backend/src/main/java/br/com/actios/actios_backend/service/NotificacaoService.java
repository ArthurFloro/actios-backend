package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.exceptions.RecursoNaoEncontradoException;
import br.com.actios.actios_backend.model.Notificacao;
import br.com.actios.actios_backend.model.Usuario;
import br.com.actios.actios_backend.model.Evento;
import br.com.actios.actios_backend.repositorys.NotificacaoRepository;
import br.com.actios.actios_backend.repositorys.UsuarioRepository;
import br.com.actios.actios_backend.repositorys.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificacaoService {
    
    @Autowired
    private NotificacaoRepository notificacaoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private EventoRepository eventoRepository;

    @Transactional
    public Notificacao criarNotificacao(Integer idUsuario, Integer idEvento, String mensagem) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));
        
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));
        
        Notificacao notificacao = new Notificacao();
        notificacao.setUsuario(usuario);
        notificacao.setEvento(evento);
        notificacao.setMensagem(mensagem);
        
        return notificacaoRepository.save(notificacao);
    }

    public List<Notificacao> listarPorUsuario(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));
        
        return notificacaoRepository.findByUsuario(usuario);
    }

    public List<Notificacao> listarPorEvento(Integer idEvento) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));
        
        return notificacaoRepository.findByEvento(evento);
    }

    public List<Notificacao> listarPorData(LocalDateTime data) {
        return notificacaoRepository.findByDataEnvioGreaterThanEqualOrderByDataEnvioDesc(data);
    }

    public long contarPorUsuario(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));
        
        return notificacaoRepository.countByUsuario(usuario);
    }

    public long contarPorEvento(Integer idEvento) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));
        
        return notificacaoRepository.countByEvento(evento);
    }

    public List<Notificacao> listarPorUsuarioEEvento(Integer idUsuario, Integer idEvento) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));
        
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));
        
        return notificacaoRepository.findByUsuarioAndEvento(usuario, evento);
    }
}
