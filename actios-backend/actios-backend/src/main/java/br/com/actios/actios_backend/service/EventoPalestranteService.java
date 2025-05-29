package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.dto.EventoDTO;
import br.com.actios.actios_backend.exceptions.CampoObrigatorioException;
import br.com.actios.actios_backend.exceptions.RecursoExistenteException;
import br.com.actios.actios_backend.exceptions.RecursoNaoEncontradoException;
import br.com.actios.actios_backend.model.Evento;
import br.com.actios.actios_backend.model.EventoPalestrante;
import br.com.actios.actios_backend.model.Palestrante;
import br.com.actios.actios_backend.model.EventoPalestranteId;
import br.com.actios.actios_backend.repositorys.EventoPalestranteRepository;
import br.com.actios.actios_backend.repositorys.EventoRepository;
import br.com.actios.actios_backend.repositorys.PalestranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
    public class EventoPalestranteService {

    @Autowired
    private EventoPalestranteRepository eventoPalestranteRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private PalestranteRepository palestranteRepository;

    public Page<EventoDTO> buscarEventosFuturosPorNomePalestrante(String nome, Pageable pageable) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new CampoObrigatorioException("O nome do palestrante deve ser informado.");
        }

        LocalDate agora = LocalDate.now();
        Page<Evento> eventos = eventoPalestranteRepository.findEventosFuturosByNomeParcialDoPalestrante(agora, nome.trim(), pageable);

        return eventos.map(EventoDTO::new);
    }

    public void associarPalestranteAoEvento(int eventoId, int palestranteId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));

        Palestrante palestrante = palestranteRepository.findById(palestranteId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Palestrante não encontrado"));

        EventoPalestranteId id = new EventoPalestranteId(evento.getIdEvento(), palestrante.getIdPalestrante());

        if (eventoPalestranteRepository.existsById(id)) {
            throw new RecursoExistenteException("Associação já existe.");
        }

        EventoPalestrante ep = new EventoPalestrante();
        ep.setEvento(evento);
        ep.setPalestrante(palestrante);
        eventoPalestranteRepository.save(ep);
    }

    public void desassociarPalestranteDoEvento(int eventoId, int palestranteId) {
        EventoPalestranteId id = new EventoPalestranteId(eventoId, palestranteId);
        if (!eventoPalestranteRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Associação não encontrada.");
        }

        eventoPalestranteRepository.deleteById(id);
    }
}

