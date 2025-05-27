package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.exceptions.RecursoExistenteException;
import br.com.actios.actios_backend.exceptions.RecursoNaoEncontradoException;
import br.com.actios.actios_backend.model.FeedbackEvento;
import br.com.actios.actios_backend.model.Usuario;
import br.com.actios.actios_backend.model.Evento;
import br.com.actios.actios_backend.repositorys.FeedbackEventoRepository;
import br.com.actios.actios_backend.repositorys.UsuarioRepository;
import br.com.actios.actios_backend.repositorys.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FeedbackEventoService {
    
    @Autowired
    private FeedbackEventoRepository feedbackEventoRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private EventoRepository eventoRepository;

    @Transactional
    public FeedbackEvento criarFeedback(Integer idUsuario, Integer idEvento, Integer nota, String comentario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));
        
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));
        
        // Verificar se já existe feedback para esse usuário e evento
        if (feedbackEventoRepository.findByUsuarioAndEvento(usuario, evento) != null) {
            throw new RecursoExistenteException("Feedback já existe para este usuário e evento");
        }
        
        FeedbackEvento feedback = new FeedbackEvento();
        feedback.setUsuario(usuario);
        feedback.setEvento(evento);
        feedback.setNota(nota);
        feedback.setComentario(comentario);
        
        return feedbackEventoRepository.save(feedback);
    }

    public List<FeedbackEvento> listarPorUsuario(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));
        
        return feedbackEventoRepository.findByUsuario(usuario);
    }

    public List<FeedbackEvento> listarPorEvento(Integer idEvento) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));
        
        return feedbackEventoRepository.findByEvento(evento);
    }

    public FeedbackEvento getFeedbackPorUsuarioEEvento(Integer idUsuario, Integer idEvento) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));

        FeedbackEvento feedback = feedbackEventoRepository.findByUsuarioAndEvento(usuario, evento);
        if (feedback == null) {
            throw new RecursoNaoEncontradoException("Feedback não encontrado para o usuário e evento informados.");
        }

        return feedback;
    }


    public long contarFeedbacksPorEvento(Integer idEvento) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));
        
        return feedbackEventoRepository.countByEvento(evento);
    }

    public double calcularMediaNotasPorEvento(Integer idEvento) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));

        Double media = feedbackEventoRepository.calcularMediaNotaPorEvento(evento);
        return media != null ? media : 0.0;
    }


    public long contarFeedbacksPorNota(Integer nota) {
        return feedbackEventoRepository.countByNota(nota);
    }
}
