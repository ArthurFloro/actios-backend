package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.exceptions.*;
import br.com.actios.actios_backend.model.Participacao;
import br.com.actios.actios_backend.model.Usuario;
import br.com.actios.actios_backend.model.Evento;
import br.com.actios.actios_backend.repositorys.ParticipacaoRepository;
import br.com.actios.actios_backend.repositorys.UsuarioRepository;
import br.com.actios.actios_backend.repositorys.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ParticipacaoService {

    private final ParticipacaoRepository participacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

    @Autowired
    public ParticipacaoService(ParticipacaoRepository participacaoRepository,
                               UsuarioRepository usuarioRepository,
                               EventoRepository eventoRepository) {
        this.participacaoRepository = participacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
    }

    @Transactional
    public Participacao registrarParticipacao(Integer idUsuario, Integer idEvento) {
        validarIds(idUsuario, idEvento);

        try {
            Usuario usuario = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário com ID " + idUsuario + " não encontrado"));

            Evento evento = eventoRepository.findById(idEvento)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Evento com ID " + idEvento + " não encontrado"));

            validarTipoUsuario(usuario);

            if (participacaoRepository.existsByUsuarioAndEvento(usuario, evento)) {
                throw new RecursoExistenteException("Usuário já está participando deste evento");
            }

            Participacao participacao = new Participacao();
            participacao.setUsuario(usuario);
            participacao.setEvento(evento);
            participacao.setCheckin(false);

            return participacaoRepository.save(participacao);
        } catch (DataAccessException e) {
            throw new OperacaoNaoPermitidaException("Falha ao registrar participação no banco de dados", e);
        }
    }

    @Transactional
    public void realizarCheckin(Integer idParticipacao) {
        if (idParticipacao == null || idParticipacao <= 0) {
            throw new CampoObrigatorioException("ID da participação é inválido");
        }

        try {
            Participacao participacao = participacaoRepository.findById(idParticipacao)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Participação com ID " + idParticipacao + " não encontrada"));

            if (participacao.getCheckin()) {
                throw new RecursoExistenteException("Check-in já realizado para esta participação");
            }

            participacao.setCheckin(true);
            participacaoRepository.save(participacao);
        } catch (DataAccessException e) {
            throw new OperacaoNaoPermitidaException("Falha ao realizar check-in no banco de dados", e);
        }
    }

    @Transactional
    public void adicionarFeedback(Integer idParticipacao, String feedback) {
        if (idParticipacao == null || idParticipacao <= 0) {
            throw new CampoObrigatorioException("ID da participação é inválido");
        }

        if (feedback == null || feedback.trim().isEmpty()) {
            throw new CampoObrigatorioException("Feedback não pode ser vazio");
        }

        try {
            Participacao participacao = participacaoRepository.findById(idParticipacao)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Participação com ID " + idParticipacao + " não encontrada"));

            if (participacao.getFeedback() != null) {
                throw new RecursoExistenteException("Feedback já registrado para esta participação");
            }

            if (!participacao.getCheckin()) {
                throw new OperacaoNaoPermitidaException("Não é possível adicionar feedback sem check-in realizado");
            }

            participacao.setFeedback(feedback);
            participacaoRepository.save(participacao);
        } catch (DataAccessException e) {
            throw new OperacaoNaoPermitidaException("Falha ao adicionar feedback no banco de dados", e);
        }
    }

    public List<Participacao> listarPorUsuario(Integer idUsuario) {
        if (idUsuario == null || idUsuario <= 0) {
            throw new CampoObrigatorioException("ID do usuário é inválido");
        }

        try {
            Usuario usuario = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário com ID " + idUsuario + " não encontrado"));

            List<Participacao> participacoes = participacaoRepository.findByUsuario(usuario);

            if (participacoes.isEmpty()) {
                throw new RecursoNaoEncontradoException("Nenhuma participação encontrada para este usuário");
            }

            return participacoes;
        } catch (DataAccessException e) {
            throw new OperacaoNaoPermitidaException("Falha ao listar participações por usuário", e);
        }
    }

    public List<Participacao> listarPorEvento(Integer idEvento) {
        if (idEvento == null || idEvento <= 0) {
            throw new CampoObrigatorioException("ID do evento é inválido");
        }

        try {
            Evento evento = eventoRepository.findById(idEvento)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Evento com ID " + idEvento + " não encontrado"));

            List<Participacao> participacoes = participacaoRepository.findByEvento(evento);

            if (participacoes.isEmpty()) {
                throw new RecursoNaoEncontradoException("Nenhuma participação encontrada para este evento");
            }

            return participacoes;
        } catch (DataAccessException e) {
            throw new OperacaoNaoPermitidaException("Falha ao listar participações por evento", e);
        }
    }

    public long contarParticipantesPorEvento(Integer idEvento) {
        if (idEvento == null || idEvento <= 0) {
            throw new CampoObrigatorioException("ID do evento é inválido");
        }

        try {
            Evento evento = eventoRepository.findById(idEvento)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Evento com ID " + idEvento + " não encontrado"));

            return participacaoRepository.countByEvento(evento);
        } catch (DataAccessException e) {
            throw new OperacaoNaoPermitidaException("Falha ao contar participantes do evento", e);
        }
    }

    public long contarCheckinsPorEvento(Integer idEvento) {
        if (idEvento == null || idEvento <= 0) {
            throw new CampoObrigatorioException("ID do evento é inválido");
        }

        try {
            Evento evento = eventoRepository.findById(idEvento)
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Evento com ID " + idEvento + " não encontrado"));

            return participacaoRepository.countByEventoAndCheckinTrue(evento);
        } catch (DataAccessException e) {
            throw new OperacaoNaoPermitidaException("Falha ao contar check-ins do evento", e);
        }
    }

    private void validarIds(Integer idUsuario, Integer idEvento) {
        if (idUsuario == null || idUsuario <= 0) {
            throw new CampoObrigatorioException("ID do usuário é inválido");
        }

        if (idEvento == null || idEvento <= 0) {
            throw new CampoObrigatorioException("ID do evento é inválido");
        }
    }

    private void validarTipoUsuario(Usuario usuario) {
        if (usuario.getTipo() != null && usuario.getTipo().equals("ORGANIZADOR")) {
            throw new TipoUsuarioInvalidoException("Organizadores não podem se inscrever como participantes");
        }
    }
}
