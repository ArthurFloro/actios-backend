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

@Service
public class ParticipacaoService {

    @Autowired
    private ParticipacaoRepository participacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoRepository eventoRepository;

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

    public List<Participacao> listarPorUsuario(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        return participacaoRepository.findByUsuario(usuario);
    }

    public List<Participacao> listarPorEvento(Integer idEvento) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));

        return participacaoRepository.findByEvento(evento);
    }

    public long contarParticipantesPorEvento(Integer idEvento) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));

        return participacaoRepository.countByEvento(evento);
    }

    public long contarCheckinsPorEvento(Integer idEvento) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));

        return participacaoRepository.countByEventoAndCheckinTrue(evento);
    }
}
