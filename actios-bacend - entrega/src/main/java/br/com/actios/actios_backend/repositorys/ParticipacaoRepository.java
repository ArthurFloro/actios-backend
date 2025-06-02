package br.com.actios.actios_backend.repositorys;

import br.com.actios.actios_backend.model.Evento;
import br.com.actios.actios_backend.model.Participacao;
import br.com.actios.actios_backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipacaoRepository extends JpaRepository<Participacao, Integer> {
    
    // Buscar participações por usuário
    public List<Participacao> findByUsuario(Usuario usuario);
    
    // Buscar participações por evento
    public List<Participacao> findByEvento(Evento evento);
    
    // Verificar se usuário já participou do evento
    public boolean existsByUsuarioAndEvento(Usuario usuario, Evento evento);
    
    // Buscar participações com check-in realizado
    public List<Participacao> findByCheckinTrue();
    
    // Buscar participações sem feedback
    public List<Participacao> findByFeedbackIsNull();
    
    // Contar participações por evento
    public long countByEvento(Evento evento);
    
    // Contar participações com check-in por evento
    public long countByEventoAndCheckinTrue(Evento evento);
}
