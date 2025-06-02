package br.com.actios.actios_backend.service;

import br.com.actios.actios_backend.dto.EventoDTO;
import br.com.actios.actios_backend.enums.FormatoEvento;
import br.com.actios.actios_backend.exceptions.CampoObrigatorioException;
import br.com.actios.actios_backend.exceptions.DataInvalidaException;
import br.com.actios.actios_backend.exceptions.RecursoNaoEncontradoException;
import br.com.actios.actios_backend.model.*;
import br.com.actios.actios_backend.repositorys.EventoRepository;
import br.com.actios.actios_backend.repositorys.EventoPalestranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private EventoPalestranteRepository eventoPalestranteRepository;

    @Autowired
    private FaculdadeService faculdadeService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private PalestranteService palestranteService;

    public Evento converterDTOParaEvento(EventoDTO dto) {
        Evento evento = new Evento();
        evento.setTitulo(dto.getTitulo());
        evento.setDescricao(dto.getDescricao());
        evento.setData(dto.getData());
        evento.setHorario(dto.getHorario());
        evento.setLocal(dto.getLocal());

        // Faculdade
        Faculdade faculdade = faculdadeService.listarTodas().stream()
                .filter(f -> f.getNome().toLowerCase().contains(dto.getNomeFaculdade().toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new RecursoNaoEncontradoException("Faculdade não encontrada: " + dto.getNomeFaculdade()));

        // Categoria
        Categoria categoria = categoriaService.listarTodas().stream()
                .filter(c -> c.getNome().equalsIgnoreCase(dto.getNomeCategoria()))
                .findFirst()
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada: " + dto.getNomeCategoria()));
        evento.setCategoria(categoria);

        // Palestrantes
        Set<EventoPalestrante> palestrantes = dto.getNomesPalestrantes().stream()
                .map(nome -> {
                    Palestrante palestrante = palestranteService.listarTodos().stream()
                            .filter(p -> p.getNome().equalsIgnoreCase(nome))
                            .findFirst()
                            .orElseThrow(() -> new RecursoNaoEncontradoException("Palestrante não encontrado: " + nome));
                    EventoPalestrante ep = new EventoPalestrante();
                    ep.setEvento(evento);
                    ep.setPalestrante(palestrante);
                    return ep;
                })
                .collect(Collectors.toSet());
        evento.setEventoPalestrantes(palestrantes);

        // Detalhes
        EventoDetalhe detalhe = new EventoDetalhe();
        detalhe.setCertificado(dto.getCertificado());
        detalhe.setValor(dto.getValor());

        // Tratamento do formato usando o método fromValue
        if (dto.getFormato() != null) {
            try {
                detalhe.setFormato(FormatoEvento.fromValue(dto.getFormato()));
            } catch (IllegalArgumentException e) {
                throw new CampoObrigatorioException("Formato de evento inválido. Valores permitidos: online, presencial, hibrido");
            }
        } else {
            throw new CampoObrigatorioException("Formato do evento é obrigatório.");
        }

        detalhe.setEvento(evento);
        evento.setDetalhes(detalhe);

        return evento;
    }


    private void validarEvento(Evento evento, boolean isAtualizacao) {
        if (isAtualizacao && (evento.getIdEvento() == null || !eventoRepository.existsById(evento.getIdEvento()))) {
            throw new RecursoNaoEncontradoException("Evento não encontrado para atualização.");
        }
        if (evento.getData() == null) {
            throw new CampoObrigatorioException("Data do evento é obrigatória.");
        }
        if (evento.getData().isBefore(LocalDate.now())) {
            throw new DataInvalidaException("Data do evento não pode ser no passado.");
        }
        if (evento.getTitulo() == null || evento.getTitulo().isBlank()) {
            throw new CampoObrigatorioException("Título do evento é obrigatório.");
        }
    }

    public Evento cadastrar(Evento evento) {
        validarEvento(evento, false);
        return eventoRepository.save(evento);
    }

    public Evento atualizar(EventoDTO dto) {
        Evento eventoExistente = eventoRepository.findById(dto.getIdEvento())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));

        // Atualiza apenas os campos permitidos
        eventoExistente.setTitulo(dto.getTitulo());
        eventoExistente.setDescricao(dto.getDescricao());

        validarEvento(eventoExistente, true);
        return eventoRepository.save(eventoExistente);
    }

    public void excluir(Integer id) {
        if (!eventoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Evento não encontrado para exclusão.");
        }
        eventoRepository.deleteById(id);
    }

    public Evento buscarPorId(Integer id) {
        return eventoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado."));
    }

    public Page<Evento> listarTodos(Pageable pageable) {
        return eventoRepository.findAll(pageable);
    }

    public Page<Evento> listarEventosFuturosPorNomePalestrante(String nome, Pageable pageable) {
        LocalDate hoje = LocalDate.now();
        return eventoPalestranteRepository.findEventosFuturosByNomeParcialDoPalestrante(hoje, nome, pageable);
    }
}