package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.exceptions.*;
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

    private final NotificacaoRepository notificacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EventoRepository eventoRepository;

    @Autowired
    public NotificacaoService(NotificacaoRepository notificacaoRepository,
                              UsuarioRepository usuarioRepository,
                              EventoRepository eventoRepository) {
        this.notificacaoRepository = notificacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventoRepository = eventoRepository;
    }

    @Transactional
    public Notificacao criarNotificacao(Integer idUsuario, Integer idEvento, String mensagem) {
        // Validações básicas
        if (idUsuario == null) {
            throw new CampoObrigatorioException("ID do usuário é obrigatório");
        }
        if (idEvento == null) {
            throw new CampoObrigatorioException("ID do evento é obrigatório");
        }
        if (mensagem == null || mensagem.isBlank()) {
            throw new CampoObrigatorioException("Mensagem da notificação é obrigatória");
        }
        if (mensagem.length() > 500) {
            throw new OperacaoNaoPermitidaException("A mensagem não pode exceder 500 caracteres");
        }

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com ID: " + idUsuario));

        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado com ID: " + idEvento));


        if (!usuarioRelacionadoAoEvento(usuario, evento)) {
            throw new OperacaoNaoPermitidaException("Usuário não está relacionado a este evento");
        }

        Notificacao notificacao = new Notificacao();
        notificacao.setUsuario(usuario);
        notificacao.setEvento(evento);
        notificacao.setMensagem(mensagem);
        notificacao.setDataEnvio(LocalDateTime.now());
        notificacao.setLida(false);

        return notificacaoRepository.save(notificacao);
    }

    public List<Notificacao> listarPorUsuario(Integer idUsuario) {
        if (idUsuario == null) {
            throw new CampoObrigatorioException("ID do usuário é obrigatório");
        }

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com ID: " + idUsuario));

        List<Notificacao> notificacoes = notificacaoRepository.findByUsuarioOrderByDataEnvioDesc(usuario);

        if (notificacoes.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhuma notificação encontrada para este usuário");
        }

        return notificacoes;
    }

    public List<Notificacao> listarPorEvento(Integer idEvento) {
        if (idEvento == null) {
            throw new CampoObrigatorioException("ID do evento é obrigatório");
        }

        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado com ID: " + idEvento));

        List<Notificacao> notificacoes = notificacaoRepository.findByEventoOrderByDataEnvioDesc(evento);

        if (notificacoes.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhuma notificação encontrada para este evento");
        }

        return notificacoes;
    }

    public List<Notificacao> listarPorData(LocalDateTime data) {
        if (data == null) {
            throw new CampoObrigatorioException("Data é obrigatória");
        }
        if (data.isAfter(LocalDateTime.now())) {
            throw new DataInvalidaException("Data não pode ser futura");
        }

        List<Notificacao> notificacoes = notificacaoRepository.findByDataEnvioGreaterThanEqualOrderByDataEnvioDesc(data);

        if (notificacoes.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhuma notificação encontrada após a data informada");
        }

        return notificacoes;
    }

    public long contarPorUsuario(Integer idUsuario) {
        if (idUsuario == null) {
            throw new CampoObrigatorioException("ID do usuário é obrigatório");
        }

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com ID: " + idUsuario));

        return notificacaoRepository.countByUsuario(usuario);
    }

    public long contarPorEvento(Integer idEvento) {
        if (idEvento == null) {
            throw new CampoObrigatorioException("ID do evento é obrigatório");
        }

        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado com ID: " + idEvento));

        return notificacaoRepository.countByEvento(evento);
    }

    public List<Notificacao> listarPorUsuarioEEvento(Integer idUsuario, Integer idEvento) {
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

        List<Notificacao> notificacoes = notificacaoRepository.findByUsuarioAndEvento(usuario, evento);

        if (notificacoes.isEmpty()) {
            throw new RecursoNaoEncontradoException("Nenhuma notificação encontrada para este usuário e evento");
        }

        return notificacoes;
    }

    @Transactional
    public void marcarComoLida(Integer idNotificacao) {
        if (idNotificacao == null) {
            throw new CampoObrigatorioException("ID da notificação é obrigatório");
        }

        Notificacao notificacao = notificacaoRepository.findById(idNotificacao)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Notificação não encontrada com ID: " + idNotificacao));

        if (notificacao.isLida()) {
            throw new OperacaoNaoPermitidaException("Notificação já está marcada como lida");
        }

        notificacao.setLida(true);
        notificacaoRepository.save(notificacao);
    }


    private boolean usuarioRelacionadoAoEvento(Usuario usuario, Evento evento) {
        // Implementar lógica para verificar se o usuário está relacionado ao evento
        // Pode ser organizador, participante, palestrante, etc.
        // Retornar true se estiver relacionado, false caso contrário
        return true; // Implementação temporária
    }
}