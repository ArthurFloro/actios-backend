package br.com.actios.actios_backend.repositorys;

import br.com.actios.actios_backend.enums.FormatoEvento;
import br.com.actios.actios_backend.model.EventoDetalhe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface EventoDetalheRepository extends JpaRepository<EventoDetalhe, Integer> {
    
    // Buscar por formato
    public List<EventoDetalhe> findByFormato(FormatoEvento formato);
    
    // Buscar por certificado
    public List<EventoDetalhe> findByCertificado(Boolean certificado);
    
    // Buscar por valor máximo
    public List<EventoDetalhe> findByValorLessThanEqual(BigDecimal valorMax);
    
    // Buscar por data de fim
    public List<EventoDetalhe> findByDataFimGreaterThanEqual(LocalDate data);
}
