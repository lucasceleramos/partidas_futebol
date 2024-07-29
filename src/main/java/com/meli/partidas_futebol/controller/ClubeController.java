package com.meli.partidas_futebol.controller;
import com.meli.partidas_futebol.Exception.ValidationException;
import com.meli.partidas_futebol.dto.ClubeDto;
import com.meli.partidas_futebol.model.Clube;
import com.meli.partidas_futebol.service.ClubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clube")


public class ClubeController {

        @Autowired
        private ClubeService clubeService;

        @PostMapping("/cadastrar")
        public ResponseEntity<Clube> cadastrarClube(@RequestBody ClubeDto clubeDto) {
                try {
                        Clube clube = clubeService.cadastrarClube(clubeDto);
                        return new ResponseEntity<>(clube, HttpStatus.CREATED);
                } catch (ValidationException e) {
                        if (e.getMessage().equals("Já existe um clube com esse nome no Estado.")){
                                return new ResponseEntity<>(null, HttpStatus.CONFLICT);
                        }
                        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
                }}

        @GetMapping("/{id}")
        public ResponseEntity<ClubeDto> buscarClube(@PathVariable Long id) {
                try {
                        ClubeDto clubeDto = clubeService.buscarClube(id);
                        return new ResponseEntity<>(clubeDto, HttpStatus.OK);
                } catch (IllegalArgumentException e) {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }}

        @PutMapping("/editar/{id}")
        public ResponseEntity<Void> editarClube(@PathVariable Long id, @RequestBody ClubeDto clubeDto) {
                try{
                        clubeService.editarClube(id, clubeDto);
                        return new ResponseEntity<>(HttpStatus.OK);
                } catch (ValidationException e) {
                        if (e.getMessage().equals("Nome do clube é obrigatório e deve ter no mínimo 2 caracteres") ||
                                e.getMessage().equals("Sigla do estado inválida") ||
                                e.getMessage().equals("Data de criação não pode ser no futuro.")) {
                                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                        } else if (e.getMessage().equals("Data de criação não pode ser posterior a alguma partida do clube.") ||
                                   e.getMessage().equals("Já existe um clube com esse nome no Estado.")) {
                                   return new ResponseEntity<>(HttpStatus.CONFLICT);
                        }
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }catch (IllegalArgumentException e){
                                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

                }}

        @DeleteMapping("/inativar/{id}")
        public ResponseEntity<Void> inativarClube(@PathVariable Long id) {
                try{
                        clubeService.inativarClube(id);
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } catch (IllegalArgumentException e){
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }}

        @GetMapping("/listar")
        public ResponseEntity<List<ClubeDto>> listarClube(
                @RequestParam(required = false) String nome,
                @RequestParam(required = false) String siglaEstado,
                @RequestParam(required = false) Boolean atividade,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size,
                @RequestParam(defaultValue = "id") String sortBy,
                @RequestParam(defaultValue = "asc") String order){
                List<ClubeDto> clubes = clubeService.listarClube(nome, siglaEstado, atividade, page, size, sortBy, order);
                return new ResponseEntity<>(clubes, HttpStatus.OK);
        }

}
