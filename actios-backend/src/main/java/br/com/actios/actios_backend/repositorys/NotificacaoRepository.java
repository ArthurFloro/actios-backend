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
    
    // Buscar notificações por usuário
    public List<Notificacao> findByUsuario(Usuario usuario);
    
    // Buscar notificações por evento
    public List<Notificacao> findByEvento(Evento evento);
    
    // Buscar notificações por data
    public List<Notificacao> findByDataEnvioGreaterThanEqualOrderByDataEnvioDesc(LocalDateTime data);
    
    // Contar notificações por usuário
    public long countByUsuario(Usuario usuario);
    
    // Contar notificações por evento
    public long countByEvento(Evento evento);
    
    // Buscar notificações por usuário e evento
    public List<Notificacao> findByUsuarioAndEvento(Usuario usuario, Evento evento);
}
