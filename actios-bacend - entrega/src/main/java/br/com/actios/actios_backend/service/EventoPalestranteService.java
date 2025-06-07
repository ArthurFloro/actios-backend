package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.dto.EventoDTO;
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
        LocalDate hoje = LocalDate.now();
        Page<Evento> eventos = eventoPalestranteRepository.findEventosFuturosByNomeParcialDoPalestrante(hoje, nome, pageable);
        return eventos.map(EventoDTO::new);
    }


    public String associarPalestranteAoEvento(int eventoId, int palestranteId) {
        Optional<Evento> eventoOpt = eventoRepository.findById(eventoId);
        Optional<Palestrante> palestranteOpt = palestranteRepository.findById(palestranteId);

        if (eventoOpt.isEmpty() || palestranteOpt.isEmpty()) {
            return "Evento ou Palestrante não encontrado.";
        }

        Evento evento = eventoOpt.get();
        Palestrante palestrante = palestranteOpt.get();

        EventoPalestranteId id = new EventoPalestranteId(evento.getIdEvento(), palestrante.getIdPalestrante());

        if (eventoPalestranteRepository.existsById(id)) {
            return "Associação já existe.";
        }

        EventoPalestrante ep = new EventoPalestrante();
        ep.setEvento(evento);
        ep.setPalestrante(palestrante);
        eventoPalestranteRepository.save(ep);

        return "Palestrante associado ao evento com sucesso.";
    }

    public String desassociarPalestranteDoEvento(int eventoId, int palestranteId) {
        EventoPalestranteId id = new EventoPalestranteId(eventoId, palestranteId);
        if (!eventoPalestranteRepository.existsById(id)) {
            return "Associação não encontrada.";
        }

        eventoPalestranteRepository.deleteById(id);
        return "Palestrante desassociado do evento com sucesso.";
    }
}
