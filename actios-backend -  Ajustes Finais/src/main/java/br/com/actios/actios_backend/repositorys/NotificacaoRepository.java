package br.com.actios.actios_backend.repositorys;

import br.com.actios.actios_backend.model.Notificacao;
import br.com.actios.actios_backend.model.Usuario;
import br.com.actios.actios_backend.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositório para operações de persistência relacionadas à entidade {@link Notificacao}.
 * <p>
 * Oferece operações para gerenciamento de notificações do sistema, incluindo buscas por
 * usuário, evento, data de envio e combinações desses critérios.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Integer> {

    /**
     * Busca todas as notificações associadas a um usuário específico.
     *
     * @param usuario Usuário destinatário das notificações
     * @return Lista de notificações do usuário, ordenadas por data de envio (decrescente)
     * @throws IllegalArgumentException se o usuário for nulo
     */
    List<Notificacao> findByUsuario(Usuario usuario);

    /**
     * Busca todas as notificações associadas a um evento específico.
     *
     * @param evento Evento relacionado às notificações
     * @return Lista de notificações do evento, ordenadas por data de envio (decrescente)
     * @throws IllegalArgumentException se o evento for nulo
     */
    List<Notificacao> findByEvento(Evento evento);

    /**
     * Busca notificações enviadas a partir de uma determinada data.
     *
     * @param data Data mínima de envio para filtrar as notificações
     * @return Lista de notificações enviadas após a data informada, ordenadas por data de envio (decrescente)
     * @throws IllegalArgumentException se a data for nula
     */
    List<Notificacao> findByDataEnvioGreaterThanEqualOrderByDataEnvioDesc(LocalDateTime data);

    /**
     * Conta o número total de notificações recebidas por um usuário.
     *
     * @param usuario Usuário destinatário das notificações
     * @return Quantidade de notificações do usuário
     * @throws IllegalArgumentException se o usuário for nulo
     */
    long countByUsuario(Usuario usuario);

    /**
     * Conta o número total de notificações associadas a um evento.
     *
     * @param evento Evento relacionado às notificações
     * @return Quantidade de notificações do evento
     * @throws IllegalArgumentException se o evento for nulo
     */
    long countByEvento(Evento evento);

    /**
     * Busca notificações específicas combinando usuário e evento.
     *
     * @param usuario Usuário destinatário das notificações
     * @param evento Evento relacionado às notificações
     * @return Lista de notificações correspondentes aos critérios, ordenadas por data de envio (decrescente)
     * @throws IllegalArgumentException se usuário ou evento forem nulos
     */
    List<Notificacao> findByUsuarioAndEvento(Usuario usuario, Evento evento);
}