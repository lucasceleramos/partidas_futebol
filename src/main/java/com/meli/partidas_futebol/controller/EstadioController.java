package com.meli.partidas_futebol.controller;
import com.meli.partidas_futebol.Exception.ValidationException;
import com.meli.partidas_futebol.dto.EstadioDto;
import com.meli.partidas_futebol.service.EstadioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/estadio")

public class EstadioController {

    @Autowired
    private EstadioService estadioService;

    @PostMapping("/cadastrar")
        public ResponseEntity<String> cadastrarEstadio(@RequestBody EstadioDto estadioDto) {
        try {
            String response = estadioService.cadastrarEstadio(estadioDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ValidationException e) {
            if (e.getMessage().equals("Já existe um estádio com esse nome.")) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }}

    @GetMapping("/{id}")
    public ResponseEntity<EstadioDto> buscarEstadio(@PathVariable Long id) {
        try{
            EstadioDto estadioDto = estadioService.buscarEstadio(id);
            return new ResponseEntity<>(estadioDto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }}

    @PutMapping("/editar/{id}")
    public ResponseEntity<String> editarEstadio(@PathVariable Long id, @RequestBody EstadioDto estadioDto) {
        try{
            estadioService.editarEstadio(id, estadioDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ValidationException e) {
            if (e.getMessage().contains("Estadio não encontrado com ID: ")) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else if (e.getMessage().equals("Nome do estádio é obrigatório e deve ter no mínimo 3 caracteres.")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } else if (e.getMessage().equals("Já existe um estádio com esse nome.")) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }}

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluirEstadio(@PathVariable Long id) {
        try{
            estadioService.excluirEstadio(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ValidationException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }}

    @GetMapping("/listar")
    public ResponseEntity<List<EstadioDto>> listarEstadio(
            @RequestParam(required = false) String nome,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String order){
        List<EstadioDto> estadios = estadioService.listarEstadio(nome, page, size, sortBy, order);
        return new ResponseEntity<>(estadios, HttpStatus.OK);
    }
}
