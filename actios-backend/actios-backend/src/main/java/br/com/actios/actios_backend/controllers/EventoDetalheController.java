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

@RestController
@RequestMapping("/api/eventos-detalhes")
public class EventoDetalheController {

    @Autowired
    private EventoDetalheService eventoDetalheService;

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

    @GetMapping("/{idEvento}")
    public ResponseEntity<EventoDetalheDTO> buscarPorEvento(@PathVariable Integer idEvento) {
        EventoDetalhe detalhe = eventoDetalheService.buscarPorEvento(idEvento);
        if (detalhe == null) {
            return ResponseEntity.notFound().build();
        }
        EventoDetalheDTO dto = new EventoDetalheDTO(detalhe);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<EventoDetalheDTO>> listarTodos() {
        List<EventoDetalhe> detalhes = eventoDetalheService.listarTodos();
        List<EventoDetalheDTO> dtoList = detalhes.stream()
                .map(EventoDetalheDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/formato/{formato}")
    public ResponseEntity<List<EventoDetalheDTO>> listarPorFormato(@PathVariable String formato) {
        List<EventoDetalhe> detalhes = eventoDetalheService.listarPorFormato(FormatoEvento.fromValue(formato));
        List<EventoDetalheDTO> dtoList = detalhes.stream()
                .map(EventoDetalheDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }


    @GetMapping("/certificado/{certificado}")
    public ResponseEntity<List<EventoDetalheDTO>> listarPorCertificado(@PathVariable Boolean certificado) {
        List<EventoDetalhe> detalhes = eventoDetalheService.listarPorCertificado(certificado);
        List<EventoDetalheDTO> dtoList = detalhes.stream()
                .map(EventoDetalheDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }


    @GetMapping("/valor-max/{valorMax}")
    public ResponseEntity<List<EventoDetalheDTO>> listarPorValorMaximo(@PathVariable BigDecimal valorMax) {
        List<EventoDetalhe> detalhes = eventoDetalheService.listarPorValorMaximo(valorMax);
        List<EventoDetalheDTO> dtoList = detalhes.stream()
                .map(EventoDetalheDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }


    @GetMapping("/data-fim/{data}")
    public ResponseEntity<List<EventoDetalheDTO>> listarPorDataFimApos(@PathVariable String data) {
        LocalDate dataFim = LocalDate.parse(data);
        List<EventoDetalhe> detalhes = eventoDetalheService.listarPorDataFimApos(dataFim);
        List<EventoDetalheDTO> dtoList = detalhes.stream()
                .map(EventoDetalheDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }


    @PutMapping("/atualizar/{idEvento}")
    public ResponseEntity<Void> atualizar(
            @PathVariable Integer idEvento,
            @RequestBody EventoDetalheDTO dto) {
        EventoDetalhe detalhe = new EventoDetalhe();

        Evento evento = new Evento();
        evento.setIdEvento(idEvento); // pega do path

        detalhe.setEvento(evento);
        detalhe.setDataFim(LocalDate.parse(dto.getDataFim()));
        detalhe.setFormato(FormatoEvento.valueOf(dto.getFormato()));
        detalhe.setCertificado(dto.getCertificado());
        detalhe.setValor(dto.getValor());

        eventoDetalheService.atualizar(idEvento, detalhe);
        return ResponseEntity.noContent().build();
    }

}
