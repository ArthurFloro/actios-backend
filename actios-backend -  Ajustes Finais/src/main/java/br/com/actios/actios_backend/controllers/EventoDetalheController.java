package br.com.actios.actios_backend.controllers;

import br.com.actios.actios_backend.dto.EventoDetalheDTO;
import br.com.actios.actios_backend.enums.FormatoEvento;
import br.com.actios.actios_backend.model.Evento;
import br.com.actios.actios_backend.model.EventoDetalhe;
import br.com.actios.actios_backend.service.EventoDetalheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador REST para operações relacionadas a detalhes de eventos.
 * <p>
 * Mapeado para a rota base "/api/eventos-detalhes" e fornece endpoints para
 * gerenciamento de informações complementares sobre eventos como formato,
 * certificado, valor e datas adicionais.
 *
 * @author Equipe Actios
 * @version 1.0
 * @since 2023-08-30
 */
@RestController
@RequestMapping("/api/eventos-detalhes")
public class EventoDetalheController {

    @Autowired
    private EventoDetalheService eventoDetalheService;

    /**
     * Salva os detalhes de um evento.
     *
     * @param dto DTO contendo os detalhes do evento a serem salvos
     * @return ResponseEntity com EventoDetalheDTO salvo (status 200) ou mensagem de erro (status 400)
     */
    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody EventoDetalheDTO dto) {
        try {
            EventoDetalhe detalhe = new EventoDetalhe();

            Evento evento = new Evento();
            evento.setIdEvento(dto.getIdEvento());

            detalhe.setEvento(evento);
            detalhe.setDataFim(LocalDate.parse(dto.getDataFim()));
            detalhe.setFormato(FormatoEvento.valueOf(dto.getFormato()));
            detalhe.setCertificado(dto.getCertificado());
            detalhe.setValor(dto.getValor());

            EventoDetalhe salvo = eventoDetalheService.salvar(detalhe);
            EventoDetalheDTO dtoSalvo = new EventoDetalheDTO(salvo);

            return ResponseEntity.ok(dtoSalvo);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao salvar EventoDetalhe: " + e.getMessage());
        }
    }

    /**
     * Busca os detalhes de um evento específico.
     *
     * @param idEvento ID do evento
     * @return ResponseEntity com EventoDetalheDTO (status 200) ou 404 se não encontrado
     */
    @GetMapping("/{idEvento}")
    public ResponseEntity<EventoDetalheDTO> buscarPorEvento(@PathVariable Integer idEvento) {
        EventoDetalhe detalhe = eventoDetalheService.buscarPorEvento(idEvento);
        if (detalhe == null) {
            return ResponseEntity.notFound().build();
        }
        EventoDetalheDTO dto = new EventoDetalheDTO(detalhe);
        return ResponseEntity.ok(dto);
    }

    /**
     * Lista todos os detalhes de eventos cadastrados.
     *
     * @return ResponseEntity com lista de EventoDetalheDTO (status 200)
     */
    @GetMapping("/listar")
    public ResponseEntity<List<EventoDetalheDTO>> listarTodos() {
        List<EventoDetalhe> detalhes = eventoDetalheService.listarTodos();
        List<EventoDetalheDTO> dtoList = detalhes.stream()
                .map(EventoDetalheDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    /**
     * Lista detalhes de eventos por formato específico.
     *
     * @param formato Formato do evento (online, presencial, hibrido)
     * @return ResponseEntity com lista de EventoDetalheDTO (status 200)
     */
    @GetMapping("/formato/{formato}")
    public ResponseEntity<List<EventoDetalheDTO>> listarPorFormato(@PathVariable String formato) {
        List<EventoDetalhe> detalhes = eventoDetalheService.listarPorFormato(FormatoEvento.fromValue(formato));
        List<EventoDetalheDTO> dtoList = detalhes.stream()
                .map(EventoDetalheDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    /**
     * Lista detalhes de eventos por disponibilidade de certificado.
     *
     * @param certificado true para eventos com certificado, false para sem certificado
     * @return ResponseEntity com lista de EventoDetalheDTO (status 200)
     */
    @GetMapping("/certificado/{certificado}")
    public ResponseEntity<List<EventoDetalheDTO>> listarPorCertificado(@PathVariable Boolean certificado) {
        List<EventoDetalhe> detalhes = eventoDetalheService.listarPorCertificado(certificado);
        List<EventoDetalheDTO> dtoList = detalhes.stream()
                .map(EventoDetalheDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    /**
     * Lista detalhes de eventos com valor até o máximo especificado.
     *
     * @param valorMax Valor máximo para filtro
     * @return ResponseEntity com lista de EventoDetalheDTO (status 200)
     */
    @GetMapping("/valor-max/{valorMax}")
    public ResponseEntity<List<EventoDetalheDTO>> listarPorValorMaximo(@PathVariable BigDecimal valorMax) {
        List<EventoDetalhe> detalhes = eventoDetalheService.listarPorValorMaximo(valorMax);
        List<EventoDetalheDTO> dtoList = detalhes.stream()
                .map(EventoDetalheDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    /**
     * Lista detalhes de eventos com data fim após a data especificada.
     *
     * @param data Data no formato ISO (yyyy-MM-dd) para filtro
     * @return ResponseEntity com lista de EventoDetalheDTO (status 200)
     */
    @GetMapping("/data-fim/{data}")
    public ResponseEntity<List<EventoDetalheDTO>> listarPorDataFimApos(@PathVariable String data) {
        LocalDate dataFim = LocalDate.parse(data);
        List<EventoDetalhe> detalhes = eventoDetalheService.listarPorDataFimApos(dataFim);
        List<EventoDetalheDTO> dtoList = detalhes.stream()
                .map(EventoDetalheDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    /**
     * Atualiza os detalhes de um evento existente.
     *
     * @param idEvento ID do evento a ser atualizado
     * @param dto DTO contendo os novos detalhes do evento
     * @return ResponseEntity vazio com status 204 (No Content)
     */
    @PutMapping("/atualizar/{idEvento}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Integer idEvento,
            @RequestBody EventoDetalheDTO dto) {
        EventoDetalhe detalhe = new EventoDetalhe();

        Evento evento = new Evento();
        evento.setIdEvento(idEvento);

        detalhe.setEvento(evento);
        detalhe.setDataFim(LocalDate.parse(dto.getDataFim()));
        detalhe.setFormato(FormatoEvento.valueOf(dto.getFormato()));
        detalhe.setCertificado(dto.getCertificado());
        detalhe.setValor(dto.getValor());

        eventoDetalheService.atualizar(idEvento, detalhe);
        return ResponseEntity.noContent().build();
    }
}