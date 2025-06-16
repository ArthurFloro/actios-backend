package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.exceptions.RecursoExistenteException;
import br.com.actios.actios_backend.exceptions.RecursoNaoEncontradoException;
import br.com.actios.actios_backend.model.Participacao;
import br.com.actios.actios_backend.model.Usuario;
import br.com.actios.actios_backend.model.Evento;
import br.com.actios.actios_backend.repositorys.ParticipacaoRepository;
import br.com.actios.actios_backend.repositorys.UsuarioRepository;
import br.com.actios.actios_backend.repositorys.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Serviço responsável por gerenciar a participação de usuários em eventos.
 * <p>
 * Controla o registro de participações, check-ins e feedbacks, além de fornecer
 * consultas sobre participações em eventos.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Service
public class ParticipacaoService {

    @Autowired
    private ParticipacaoRepository participacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoRepository eventoRepository;

    /**
     * Registra a participação de um usuário em um evento.
     *
     * @param idUsuario ID do usuário que está participando
     * @param idEvento ID do evento no qual o usuário está participando
     * @return Participação registrada
     * @throws RecursoNaoEncontradoException se usuário ou evento não forem encontrados
     * @throws RecursoExistenteException se o usuário já estiver participando do evento
     */
    @Transactional
    public Participacao registrarParticipacao(Integer idUsuario, Integer idEvento) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        if (participacaoRepository.existsByUsuarioAndEvento(usuario, evento)) {
            throw new RecursoExistenteException("Usuário já está participando deste evento");
        }

        Participacao participacao = new Participacao();
        participacao.setUsuario(usuario);
        participacao.setEvento(evento);
        participacao.setCheckin(false);

        return participacaoRepository.save(participacao);
    }

    /**
     * Realiza o check-in de um participante em um evento.
     *
     * @param idParticipacao ID da participação a ser confirmada
     * @throws RecursoNaoEncontradoException se a participação não for encontrada
     * @throws RecursoExistenteException se o check-in já tiver sido realizado
     */
    @Transactional
    public void realizarCheckin(Integer idParticipacao) {
        Participacao participacao = participacaoRepository.findById(idParticipacao)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Participação não encontrada"));

        if (participacao.getCheckin()) {
            throw new RecursoExistenteException("Check-in já realizado para esta participação");
        }

        participacao.setCheckin(true);
        participacaoRepository.save(participacao);
    }

    /**
     * Adiciona um feedback à participação em um evento.
     *
     * @param idParticipacao ID da participação a receber o feedback
     * @param feedback Texto do feedback
     * @throws RecursoNaoEncontradoException se a participação não for encontrada
     * @throws RecursoExistenteException se já existir feedback para esta participação
     */
    @Transactional
    public void adicionarFeedback(Integer idParticipacao, String feedback) {
        Participacao participacao = participacaoRepository.findById(idParticipacao)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Participação não encontrada"));

        if (participacao.getFeedback() != null) {
            throw new RecursoExistenteException("Feedback já registrado para esta participação");
        }

        participacao.setFeedback(feedback);
        participacaoRepository.save(participacao);
    }

    /**
     * Lista todas as participações de um usuário.
     *
     * @param idUsuario ID do usuário
     * @return Lista de participações do usuário
     * @throws RecursoNaoEncontradoException se o usuário não for encontrado
     */
    public List<Participacao> listarPorUsuario(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        return participacaoRepository.findByUsuario(usuario);
    }

    /**
     * Lista todas as participações em um evento específico.
     *
     * @param idEvento ID do evento
     * @return Lista de participações no evento
     * @throws RecursoNaoEncontradoException se o evento não for encontrado
     */
    public List<Participacao> listarPorEvento(Integer idEvento) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));

        return participacaoRepository.findByEvento(evento);
    }

    /**
     * Conta o número total de participantes em um evento.
     *
     * @param idEvento ID do evento
     * @return Número total de participantes
     * @throws RecursoNaoEncontradoException se o evento não for encontrado
     */
    public long contarParticipantesPorEvento(Integer idEvento) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));

        return participacaoRepository.countByEvento(evento);
    }

    /**
     * Conta o número de check-ins realizados em um evento.
     *
     * @param idEvento ID do evento
     * @return Número de check-ins realizados
     * @throws RecursoNaoEncontradoException se o evento não for encontrado
     */
    public long contarCheckinsPorEvento(Integer idEvento) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));

        return participacaoRepository.countByEventoAndCheckinTrue(evento);
    }
}