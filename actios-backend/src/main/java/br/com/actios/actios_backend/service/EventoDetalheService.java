package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.enums.FormatoEvento;
import br.com.actios.actios_backend.exceptions.CampoObrigatorioException;
import br.com.actios.actios_backend.exceptions.RecursoNaoEncontradoException;
import br.com.actios.actios_backend.model.Evento;
import br.com.actios.actios_backend.model.EventoDetalhe;
import br.com.actios.actios_backend.repositorys.EventoDetalheRepository;
import br.com.actios.actios_backend.repositorys.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Collections;

@Service
public class EventoDetalheService {
    
    @Autowired
    private EventoDetalheRepository detalheRepository;
    
    @Autowired
    private EventoRepository eventoRepository;

    @Transactional
    public EventoDetalhe salvar(EventoDetalhe detalhe) {
        if (detalhe.getEvento() == null || detalhe.getEvento().getIdEvento() == null) {
            throw new CampoObrigatorioException("Evento é obrigatório");
        }
        
        Evento evento = eventoRepository.findById(detalhe.getEvento().getIdEvento())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));
        
        detalhe.setEvento(evento);
        return detalheRepository.save(detalhe);
    }

    public EventoDetalhe buscarPorEvento(Integer idEvento) {
        return detalheRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Detalhes do evento não encontrados"));
    }

    public List<EventoDetalhe> listarTodos() {
        return detalheRepository.findAll();
    }

    public List<EventoDetalhe> listarPorFormato(FormatoEvento formato) {
        return detalheRepository.findByFormato(formato);
    }

    public List<EventoDetalhe> listarPorCertificado(Boolean certificado) {
        return detalheRepository.findByCertificado(certificado);
    }

    public List<EventoDetalhe> listarPorValorMaximo(BigDecimal valorMax) {
        return detalheRepository.findByValorLessThanEqual(valorMax);
    }

    public List<EventoDetalhe> listarPorDataFimApos(LocalDate data) {
        return detalheRepository.findByDataFimGreaterThanEqual(data);
    }

    @Transactional
    public void atualizar(Integer idEvento, EventoDetalhe detalheAtualizado) {
        EventoDetalhe detalhe = detalheRepository.findById(idEvento)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Detalhes do evento com ID " + idEvento + " não encontrados"));

        if (detalheAtualizado.getDataFim() != null) {
            detalhe.setDataFim(detalheAtualizado.getDataFim());
        }

        if (detalheAtualizado.getFormato() != null) {
            detalhe.setFormato(detalheAtualizado.getFormato());
        }

        if (detalheAtualizado.getCertificado() != null) {
            detalhe.setCertificado(detalheAtualizado.getCertificado());
        }

        if (detalheAtualizado.getValor() != null) {
            detalhe.setValor(detalheAtualizado.getValor());
        }

        detalheRepository.save(detalhe);
    }

}
