package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.exceptions.DataInvalidaException;
import br.com.actios.actios_backend.exceptions.RecursoExistenteException;
import br.com.actios.actios_backend.exceptions.RecursoNaoEncontradoException;
import br.com.actios.actios_backend.exceptions.TipoUsuarioInvalidoException;
import br.com.actios.actios_backend.model.Evento;
import br.com.actios.actios_backend.model.Inscricao;
import br.com.actios.actios_backend.model.Usuario;
import br.com.actios.actios_backend.repositorys.EventoRepository;
import br.com.actios.actios_backend.repositorys.InscricaoRepository;
import br.com.actios.actios_backend.repositorys.UsuarioRepository;
import br.com.actios.actios_backend.enums.TipoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Serviço responsável por gerenciar inscrições de usuários em eventos.
 * <p>
 * Controla o ciclo de vida das inscrições, incluindo criação, listagem e cancelamento,
 * com validações de negócio e tratamento de exceções específicas.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Service
@Transactional
public class InscricaoService {

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Realiza a inscrição de um usuário em um evento.
     *
     * @param idUsuario ID do usuário que deseja se inscrever (não pode ser nulo)
     * @param idEvento ID do evento no qual o usuário deseja se inscrever (não pode ser nulo)
     * @return Inscricao criada e persistida
     * @throws RecursoNaoEncontradoException se usuário ou evento não forem encontrados
     * @throws TipoUsuarioInvalidoException se o usuário não for do tipo ALUNO
     * @throws DataInvalidaException se o evento já tiver ocorrido
     * @throws RecursoExistenteException se o usuário já estiver inscrito no evento
     * @throws IllegalArgumentException se algum ID for nulo
     */
    @Transactional
    public Inscricao inscrever(Integer idUsuario, Integer idEvento) {
        if (idUsuario == null || idEvento == null) {
            throw new IllegalArgumentException("IDs do usuário e do evento são obrigatórios");
        }

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado."));
        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado."));

        if (usuario.getTipo() != TipoUsuario.ALUNO) {
            throw new TipoUsuarioInvalidoException("Apenas usuários do tipo 'ALUNO' podem se inscrever em eventos.");
        }

        if (evento.getData().isBefore(LocalDate.now())) {
            throw new DataInvalidaException("Não é possível se inscrever em eventos passados.");
        }

        if (inscricaoRepository.existsByUsuarioAndEvento(usuario, evento)) {
            throw new RecursoExistenteException("Usuário já está inscrito neste evento.");
        }

        Inscricao inscricao = new Inscricao();
        inscricao.setUsuario(usuario);
        inscricao.setEvento(evento);
        inscricao.setNumeroInscricao(gerarNumeroInscricaoUnico());
        inscricao.setDataInscricao(LocalDateTime.now());

        return inscricaoRepository.save(inscricao);
    }

    /**
     * Lista todas as inscrições existentes no sistema.
     *
     * @return Lista de todas as inscrições ordenadas por data de inscrição
     */
    @Transactional(readOnly = true)
    public List<Inscricao> listarTodas() {
        return inscricaoRepository.findAllByOrderByDataInscricaoDesc();
    }

    /**
     * Cancela uma inscrição existente.
     *
     * @param idInscricao ID da inscrição a ser cancelada (não pode ser nulo)
     * @throws RecursoNaoEncontradoException se a inscrição não for encontrada
     * @throws IllegalArgumentException se o ID da inscrição for nulo
     */
    @Transactional
    public void cancelarInscricao(Integer idInscricao) {
        if (idInscricao == null) {
            throw new IllegalArgumentException("ID da inscrição é obrigatório");
        }

        Inscricao inscricao = inscricaoRepository.findById(idInscricao)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Inscrição não encontrada."));

        inscricaoRepository.delete(inscricao);
    }

    /**
     * Gera um número único para identificação da inscrição.
     *
     * @return String contendo um UUID único
     */
    private String gerarNumeroInscricaoUnico() {
        return UUID.randomUUID().toString();
    }
}