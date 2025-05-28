package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.exceptions.*;
import br.com.actios.actios_backend.model.FeedbackEvento;
import br.com.actios.actios_backend.model.Usuario;
import br.com.actios.actios_backend.model.Evento;
import br.com.actios.actios_backend.repositorys.FeedbackEventoRepository;
import br.com.actios.actios_backend.repositorys.UsuarioRepository;
import br.com.actios.actios_backend.repositorys.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class FeedbackEventoService {

    private final FeedbackEventoRepository feedbackEventoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

    @Autowired
    public FeedbackEventoService(FeedbackEventoRepository feedbackEventoRepository,
                                 UsuarioRepository usuarioRepository,
                                 EventoRepository eventoRepository) {
        this.feedbackEventoRepository = feedbackEventoRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
    }

    @Transactional
    public FeedbackEvento criarFeedback(Integer idUsuario, Integer idEvento, Integer nota, String comentario) {
        // Validações básicas
        if (idUsuario == null) {
            throw new CampoObrigatorioException("ID do usuário é obrigatório");
        }
        if (idEvento == null) {
            throw new CampoObrigatorioException("ID do evento é obrigatório");
        }
        if (nota == null) {
            throw new CampoObrigatorioException("Nota é obrigatória");
        }

        // Validações de negócio
        if (nota < 1 || nota > 5) {
            throw new OperacaoNaoPermitidaException("A nota deve estar entre 1 e 5");
        }

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com ID: " + idUsuario));

        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado com ID: " + idEvento));

        // Verificar se o evento já ocorreu
        if (evento.getData().isAfter(LocalDate.now())) {
            throw new DataInvalidaException("Não é possível avaliar um evento que ainda não ocorreu");
        }

        // Verificar se usuário participou do evento
        if (!usuarioParticipouDoEvento(usuario, evento)) {
            throw new OperacaoNaoPermitidaException("Usuário não participou deste evento e não pode avaliá-lo");
        }

        // Verificar se já existe feedback
        if (feedbackEventoRepository.existsByUsuarioAndEvento(usuario, evento)) {
            throw new RecursoExistenteException("Já existe um feedback deste usuário para este evento");
        }

        FeedbackEvento feedback = new FeedbackEvento();
        feedback.setUsuario(usuario);
        feedback.setEvento(evento);
        feedback.setNota(nota);
        feedback.setComentario(comentario);


// Então pode simplesmente remover esta linha, porque já é setado no construtor.

        return feedbackEventoRepository.save(feedback);
    }

    public List<FeedbackEvento> listarPorUsuario(Integer idUsuario) {
        if (idUsuario == null) {
            throw new CampoObrigatorioException("ID do usuário é obrigatório");
        }

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com ID: " + idUsuario));

        List<FeedbackEvento> feedbacks = feedbackEventoRepository.findByUsuario(usuario);

        if (feedbacks.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum feedback encontrado para este usuário");
        }

        return feedbacks;
    }

    public List<FeedbackEvento> listarPorEvento(Integer idEvento) {
        if (idEvento == null) {
            throw new CampoObrigatorioException("ID do evento é obrigatório");
        }

        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado com ID: " + idEvento));

        List<FeedbackEvento> feedbacks = feedbackEventoRepository.findByEvento(evento);

        if (feedbacks.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhum feedback encontrado para este evento");
        }

        return feedbacks;
    }

    public FeedbackEvento buscarPorUsuarioEEvento(Integer idUsuario, Integer idEvento) {
        if (idUsuario == null) {
            throw new CampoObrigatorioException("ID do usuário é obrigatório");
        }
        if (idEvento == null) {
            throw new CampoObrigatorioException("ID do evento é obrigatório");
        }

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com ID: " + idUsuario));

        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado com ID: " + idEvento));

        return feedbackEventoRepository.findByUsuarioAndEvento(usuario, evento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Feedback não encontrado para o usuário e evento informados"));
    }

    public long contarFeedbacksPorEvento(Integer idEvento) {
        if (idEvento == null) {
            throw new CampoObrigatorioException("ID do evento é obrigatório");
        }

        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado com ID: " + idEvento));

        return feedbackEventoRepository.countByEvento(evento);
    }

    public double calcularMediaNotasPorEvento(Integer idEvento) {
        if (idEvento == null) {
            throw new CampoObrigatorioException("ID do evento é obrigatório");
        }

        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado com ID: " + idEvento));

        Double media = feedbackEventoRepository.calcularMediaNotaPorEvento(evento);

        if (media == null) {
            throw new RecursoNaoEncontradoException("Nenhum feedback encontrado para calcular a média");
        }

        return media;
    }

    public long contarFeedbacksPorNota(Integer nota) {
        if (nota == null) {
            throw new CampoObrigatorioException("Nota é obrigatória");
        }
        if (nota < 1 || nota > 5) {
            throw new OperacaoNaoPermitidaException("A nota deve estar entre 1 e 5");
        }

        return feedbackEventoRepository.countByNota(nota);
    }

    private boolean usuarioParticipouDoEvento(Usuario usuario, Evento evento) {
        // Implementar lógica para verificar se o usuário realmente participou do evento
        // Pode ser através de inscrições confirmadas, presença registrada, etc.
        // Retornar true se participou, false caso contrário
        return true; // Implementação temporária
    }
}