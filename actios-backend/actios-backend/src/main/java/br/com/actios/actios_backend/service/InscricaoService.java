package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.exceptions.*;
import br.com.actios.actios_backend.model.Evento;
import br.com.actios.actios_backend.model.Inscricao;
import br.com.actios.actios_backend.model.Usuario;
import br.com.actios.actios_backend.repositorys.EventoRepository;
import br.com.actios.actios_backend.repositorys.InscricaoRepository;
import br.com.actios.actios_backend.repositorys.UsuarioRepository;
import br.com.actios.actios_backend.enums.TipoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InscricaoService {

    private final InscricaoRepository inscricaoRepository;
    private final EventoRepository eventoRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public InscricaoService(InscricaoRepository inscricaoRepository,
                            EventoRepository eventoRepository,
                            UsuarioRepository usuarioRepository) {
        this.inscricaoRepository = inscricaoRepository;
        this.eventoRepository = eventoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Inscricao inscrever(Integer idUsuario, Integer idEvento) {
        if (idUsuario == null) {
            throw new CampoObrigatorioException("ID do usuário é obrigatório.");
        }
        if (idEvento == null) {
            throw new CampoObrigatorioException("ID do evento é obrigatório.");
        }

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com ID: " + idUsuario));

        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado com ID: " + idEvento));

        validarInscricao(usuario, evento);

        Inscricao inscricao = new Inscricao();
        inscricao.setUsuario(usuario);
        inscricao.setEvento(evento);
        inscricao.setNumeroInscricao(gerarNumeroInscricaoUnico());
        inscricao.setDataInscricao(LocalDateTime.now());
        inscricao.setAtivo(true);

        return inscricaoRepository.save(inscricao);
    }

    public List<Inscricao> listarTodas() {
        List<Inscricao> inscricoes = inscricaoRepository.findAll();

        if (inscricoes.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhuma inscrição encontrada.");
        }

        return inscricoes;
    }

    public List<Inscricao> listarPorUsuario(Integer idUsuario) {
        if (idUsuario == null) {
            throw new CampoObrigatorioException("ID do usuário é obrigatório.");
        }

        if (!usuarioRepository.existsById(idUsuario)) {
            throw new RecursoNaoEncontradoException("Usuário não encontrado com ID: " + idUsuario);
        }

        List<Inscricao> inscricoes = inscricaoRepository.findByUsuarioId(idUsuario);

        if (inscricoes.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhuma inscrição encontrada para este usuário.");
        }

        return inscricoes;
    }

    public List<Inscricao> listarPorEvento(Integer idEvento) {
        if (idEvento == null) {
            throw new CampoObrigatorioException("ID do evento é obrigatório.");
        }

        if (!eventoRepository.existsById(idEvento)) {
            throw new RecursoNaoEncontradoException("Evento não encontrado com ID: " + idEvento);
        }

        List<Inscricao> inscricoes = inscricaoRepository.findByEventoId(idEvento);

        if (inscricoes.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhuma inscrição encontrada para este evento.");
        }

        return inscricoes;
    }

    public void cancelarInscricao(Integer idInscricao) {
        if (idInscricao == null) {
            throw new CampoObrigatorioException("ID da inscrição é obrigatório.");
        }

        Inscricao inscricao = inscricaoRepository.findById(idInscricao)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Inscrição não encontrada com ID: " + idInscricao));

        if (!inscricao.isAtivo()) {
            throw new OperacaoNaoPermitidaException("Esta inscrição já está cancelada.");
        }

        if (inscricao.getEvento().getData().isBefore(LocalDate.now())) {
            throw new OperacaoNaoPermitidaException("Não é possível cancelar inscrição em eventos já realizados.");
        }

        inscricao.setAtivo(false);
        inscricaoRepository.save(inscricao);
    }

    private void validarInscricao(Usuario usuario, Evento evento) {
        if (usuario.getTipo() != TipoUsuario.ALUNO) {
            throw new TipoUsuarioInvalidoException("Apenas usuários do tipo 'ALUNO' podem se inscrever em eventos.");
        }

        if (evento.getData().isBefore(LocalDate.now())) {
            throw new DataInvalidaException("Não é possível se inscrever em eventos passados.");
        }

        if (!evento.isAtivo()) {
            throw new OperacaoNaoPermitidaException("Este evento não está ativo para inscrições.");
        }

        Optional<Inscricao> existente = inscricaoRepository.findByUsuarioAndEvento(usuario, evento);
        if (existente.isPresent() && existente.get().isAtivo()) {
            throw new RecursoExistenteException("Usuário já está inscrito neste evento.");
        }
    }

    private String gerarNumeroInscricaoUnico() {
        String numeroInscricao;
        do {
            numeroInscricao = UUID.randomUUID().toString();
        } while (inscricaoRepository.existsByNumeroInscricao(numeroInscricao));

        return numeroInscricao;
    }
}