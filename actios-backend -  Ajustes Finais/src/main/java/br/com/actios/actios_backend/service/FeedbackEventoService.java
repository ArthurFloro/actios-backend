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
import java.util.Optional;

/**
 * Serviço responsável pelas operações relacionadas a feedbacks de eventos.
 * <p>
 * Gerencia a criação, consulta e análise de feedbacks associados a eventos
 * e usuários do sistema.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Service
@Transactional
public class FeedbackEventoService {

    @Autowired
    private FeedbackEventoRepository feedbackEventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoRepository eventoRepository;

    /**
     * Cria um novo feedback para um evento específico.
     *
     * @param idUsuario ID do usuário que está enviando o feedback (não pode ser nulo)
     * @param idEvento ID do evento que está sendo avaliado (não pode ser nulo)
     * @param nota Nota atribuída ao evento (deve estar entre 1 e 5)
     * @param comentario Comentário sobre o evento (opcional)
     * @return FeedbackEvento criado e persistido
     * @throws RecursoNaoEncontradoException se usuário ou evento não forem encontrados
     * @throws RecursoExistenteException se já existir feedback deste usuário para o evento
     * @throws IllegalArgumentException se algum parâmetro obrigatório for inválido
     */
    @Transactional
    public FeedbackEvento criarFeedback(Integer idUsuario, Integer idEvento, Integer nota, String comentario) {
        if (idUsuario == null || idEvento == null) {
            throw new IllegalArgumentException("ID do usuário e do evento são obrigatórios");
        }

        if (nota == null || nota < 1 || nota > 5) {
            throw new IllegalArgumentException("Nota deve estar entre 1 e 5");
        }

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));

        if (feedbackEventoRepository.existsByUsuarioAndEvento(usuario, evento)) {
            throw new RecursoExistenteException("Feedback já existe para este usuário e evento");
        }

        FeedbackEvento feedback = new FeedbackEvento();
        feedback.setUsuario(usuario);
        feedback.setEvento(evento);
        feedback.setNota(nota);
        feedback.setComentario(comentario);

        return feedbackEventoRepository.save(feedback);
    }

    /**
     * Lista todos os feedbacks de um usuário específico.
     *
     * @param idUsuario ID do usuário (não pode ser nulo)
     * @return Lista de feedbacks associados ao usuário
     * @throws RecursoNaoEncontradoException se o usuário não for encontrado
     * @throws IllegalArgumentException se o ID do usuário for nulo
     */
    @Transactional(readOnly = true)
    public List<FeedbackEvento> listarPorUsuario(Integer idUsuario) {
        if (idUsuario == null) {
            throw new IllegalArgumentException("ID do usuário é obrigatório");
        }

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        return feedbackEventoRepository.findByUsuario(usuario);
    }

    /**
     * Lista todos os feedbacks de um evento específico.
     *
     * @param idEvento ID do evento (não pode ser nulo)
     * @return Lista de feedbacks associados ao evento
     * @throws RecursoNaoEncontradoException se o evento não for encontrado
     * @throws IllegalArgumentException se o ID do evento for nulo
     */
    @Transactional(readOnly = true)
    public List<FeedbackEvento> listarPorEvento(Integer idEvento) {
        if (idEvento == null) {
            throw new IllegalArgumentException("ID do evento é obrigatório");
        }

        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));

        return feedbackEventoRepository.findByEvento(evento);
    }

    /**
     * Obtém um feedback específico de um usuário para um evento.
     *
     * @param idUsuario ID do usuário (não pode ser nulo)
     * @param idEvento ID do evento (não pode ser nulo)
     * @return Feedback encontrado
     * @throws RecursoNaoEncontradoException se usuário, evento ou feedback não forem encontrados
     * @throws IllegalArgumentException se algum ID for nulo
     */
    @Transactional(readOnly = true)
    public FeedbackEvento getFeedbackPorUsuarioEEvento(Integer idUsuario, Integer idEvento) {
        if (idUsuario == null || idEvento == null) {
            throw new IllegalArgumentException("IDs do usuário e do evento são obrigatórios");
        }

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));

        return feedbackEventoRepository.findByUsuarioAndEvento(usuario, evento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Feedback não encontrado para o usuário e evento informados"));
    }

    /**
     * Conta a quantidade de feedbacks recebidos por um evento.
     *
     * @param idEvento ID do evento (não pode ser nulo)
     * @return Quantidade de feedbacks
     * @throws RecursoNaoEncontradoException se o evento não for encontrado
     * @throws IllegalArgumentException se o ID do evento for nulo
     */
    @Transactional(readOnly = true)
    public long contarFeedbacksPorEvento(Integer idEvento) {
        if (idEvento == null) {
            throw new IllegalArgumentException("ID do evento é obrigatório");
        }

        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));

        return feedbackEventoRepository.countByEvento(evento);
    }

    /**
     * Calcula a média das notas dos feedbacks de um evento.
     *
     * @param idEvento ID do evento (não pode ser nulo)
     * @return Média das notas (0.0 se não houver feedbacks)
     * @throws RecursoNaoEncontradoException se o evento não for encontrado
     * @throws IllegalArgumentException se o ID do evento for nulo
     */
    @Transactional(readOnly = true)
    public double calcularMediaNotasPorEvento(Integer idEvento) {
        if (idEvento == null) {
            throw new IllegalArgumentException("ID do evento é obrigatório");
        }

        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));

        Double media = feedbackEventoRepository.calcularMediaNotaPorEvento(evento);
        return media != null ? media : 0.0;
    }

    /**
     * Conta a quantidade de feedbacks com uma nota específica em todo o sistema.
     *
     * @param nota Nota a ser contabilizada (deve estar entre 1 e 5)
     * @return Quantidade de feedbacks com a nota informada
     * @throws IllegalArgumentException se a nota for inválida
     */
    @Transactional(readOnly = true)
    public long contarFeedbacksPorNota(Integer nota) {
        if (nota == null || nota < 1 || nota > 5) {
            throw new IllegalArgumentException("Nota deve estar entre 1 e 5");
        }

        return feedbackEventoRepository.countByNota(nota);
    }
}