package com.meli.partidas_futebol.controller;

import com.meli.partidas_futebol.dto.PartidaDto;
import com.meli.partidas_futebol.model.Partida;
import com.meli.partidas_futebol.service.PartidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping

public class PartidaController {

    @Autowired
    private PartidaService partidaService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Partida> cadastrarPartida(@RequestBody PartidaDto partidaDto) {
        Partida partida = partidaService.cadastrarPartida(partidaDto);
        return new ResponseEntity<>(partida, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity buscarPartida(@PathVariable Long id) {
        return ResponseEntity.ok(partidaService.buscarPartida(id));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<String> editarPartida(@PathVariable Long id, @RequestBody PartidaDto partidaDto) {
        partidaService.editarPartida(id, partidaDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluirPartida(@PathVariable Long id) {
        partidaService.excluirPartida(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<Partida>> listarPartida(
            @RequestParam(required = false) Long clubeId,
            @RequestParam(required = false) Long estadioId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order) {
        Page<Partida> partidas = partidaService.listarPartida(clubeId, estadioId, page, size, sortBy, order);
        return new ResponseEntity<>(partidas, HttpStatus.OK);
    }
}
