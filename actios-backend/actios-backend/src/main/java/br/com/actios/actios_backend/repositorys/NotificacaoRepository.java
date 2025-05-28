package br.com.actios.actios_backend.repositorys;

import br.com.actios.actios_backend.model.Notificacao;
import br.com.actios.actios_backend.model.Usuario;
import br.com.actios.actios_backend.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Integer> {

    List<Notificacao> findByUsuario(Usuario usuario);

    List<Notificacao> findByEvento(Evento evento);

    List<Notificacao> findByDataEnvioGreaterThanEqualOrderByDataEnvioDesc(LocalDateTime data);

    List<Notificacao> findByUsuarioOrderByDataEnvioDesc(Usuario usuario);

    List<Notificacao> findByEventoOrderByDataEnvioDesc(Evento evento);

    long countByUsuario(Usuario usuario);

    long countByEvento(Evento evento);

    List<Notificacao> findByUsuarioAndEvento(Usuario usuario, Evento evento);
}
