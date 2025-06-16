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

import java.time.LocalDateTime;
import java.util.List;

/**
 * Serviço responsável pelo gerenciamento de notificações do sistema.
 * <p>
 * Oferece operações para criar, listar e contar notificações associadas a usuários e eventos.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Service
public class NotificacaoService {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoRepository eventoRepository;

    /**
     * Cria uma nova notificação associada a um usuário e evento.
     *
     * @param idUsuario ID do usuário que receberá a notificação
     * @param idEvento ID do evento relacionado à notificação
     * @param mensagem Conteúdo textual da notificação
     * @return Notificação criada e persistida
     * @throws RecursoNaoEncontradoException Se o usuário ou evento não forem encontrados
     */
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

    /**
     * Lista todas as notificações de um usuário.
     *
     * @param idUsuario ID do usuário
     * @return Lista de notificações do usuário
     * @throws RecursoNaoEncontradoException Se o usuário não for encontrado
     */
    public List<Notificacao> listarPorUsuario(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        return notificacaoRepository.findByUsuario(usuario);
    }

    /**
     * Lista todas as notificações relacionadas a um evento.
     *
     * @param idEvento ID do evento
     * @return Lista de notificações do evento
     * @throws RecursoNaoEncontradoException Se o evento não for encontrado
     */
    public List<Notificacao> listarPorEvento(Integer idEvento) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));

        return notificacaoRepository.findByEvento(evento);
    }

    /**
     * Lista notificações enviadas após uma determinada data.
     *
     * @param data Data de corte para a consulta
     * @return Lista de notificações ordenadas da mais recente para a mais antiga
     */
    public List<Notificacao> listarPorData(LocalDateTime data) {
        return notificacaoRepository.findByDataEnvioGreaterThanEqualOrderByDataEnvioDesc(data);
    }

    /**
     * Conta o total de notificações de um usuário.
     *
     * @param idUsuario ID do usuário
     * @return Quantidade total de notificações
     * @throws RecursoNaoEncontradoException Se o usuário não for encontrado
     */
    public long contarPorUsuario(Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        return notificacaoRepository.countByUsuario(usuario);
    }

    /**
     * Conta o total de notificações de um evento.
     *
     * @param idEvento ID do evento
     * @return Quantidade total de notificações
     * @throws RecursoNaoEncontradoException Se o evento não for encontrado
     */
    public long contarPorEvento(Integer idEvento) {
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));

        return notificacaoRepository.countByEvento(evento);
    }

    /**
     * Lista notificações específicas de um usuário para um evento.
     *
     * @param idUsuario ID do usuário
     * @param idEvento ID do evento
     * @return Lista de notificações do usuário para o evento especificado
     * @throws RecursoNaoEncontradoException Se usuário ou evento não forem encontrados
     */
    public List<Notificacao> listarPorUsuarioEEvento(Integer idUsuario, Integer idEvento) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado"));

        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));

        return notificacaoRepository.findByUsuarioAndEvento(usuario, evento);
    }
}