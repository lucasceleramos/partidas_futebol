package com.meli.partidas_futebol.service;
import com.meli.partidas_futebol.dto.PartidaDto;
import com.meli.partidas_futebol.model.Clube;
import com.meli.partidas_futebol.model.Estadio;
import com.meli.partidas_futebol.model.Partida;
import com.meli.partidas_futebol.repository.ClubeRepository;
import com.meli.partidas_futebol.repository.EstadioRepository;
import com.meli.partidas_futebol.repository.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service


public class PartidaService {

    @Autowired
    private PartidaRepository partidaRepository;
    @Autowired
    private ClubeRepository clubeRepository;
    @Autowired
    private EstadioRepository estadioRepository;

    public Partida cadastrarPartida(PartidaDto partidaDto) {
        Clube clubeMandante = clubeRepository.findById(partidaDto.getClubeMandanteId())
                .orElseThrow(() -> new RuntimeException("Clube mandante não encontrado"));
        Clube clubeVisitante = clubeRepository.findById(partidaDto.getClubeVisitanteId())
                .orElseThrow(() -> new RuntimeException("Clube visitante não encontrado"));
        Estadio estadio = estadioRepository.findById(partidaDto.getEstadioId())
                .orElseThrow(() -> new RuntimeException("Estádio não encontrado"));

        Partida partida = new Partida();
        partida.setClubeMandante(clubeMandante);
        partida.setClubeVisitante(clubeVisitante);
        partida.setResultado(partidaDto.getResultado());
        partida.setDataHora(partidaDto.getDataHora());
        partida.setEstadio(estadio);
        partida.setGolsMandante(partidaDto.getGolsMandante());
        partida.setGolsVisitante(partidaDto.getGolsVisitante());

        return partidaRepository.save(partida);
    }

    public String buscarPartida(Long id) {
        Partida partida = partidaRepository.findById(id).orElse(null);
        if (partida == null) return "Partida não encontrada!";
        return partida.toString();
    }

    public void editarPartida(Long id, PartidaDto partidaDto) {
        Partida partida = partidaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Partida não encontrada com ID: " + id));
        Clube clubeMandante = clubeRepository.findById(partidaDto.getClubeMandanteId())
                .orElseThrow(() -> new RuntimeException("Clube mandante não encontrado"));
        Clube clubeVisitante = clubeRepository.findById(partidaDto.getClubeVisitanteId())
                .orElseThrow(() -> new RuntimeException("Clube visitante não encontrado"));
        Estadio estadio = estadioRepository.findById(partidaDto.getEstadioId())
                .orElseThrow(() -> new RuntimeException("Estádio não encontrado"));

        partida.setClubeMandante(clubeMandante);
        partida.setClubeVisitante(clubeVisitante);
        partida.setResultado(partidaDto.getResultado());
        partida.setDataHora(partidaDto.getDataHora());
        partida.setEstadio(estadio);
        partida.setGolsMandante(partidaDto.getGolsMandante());
        partida.setGolsVisitante(partidaDto.getGolsVisitante());
        partidaRepository.save(partida);
    }

    public void excluirPartida(Long id) {
        Partida partida = partidaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Partida não encontrada com ID: " + id));
        partidaRepository.delete(partida);
    }

    public Page<Partida> listarPartida(Long clubeId, Long estadioId, int page, int size, String sortBy, String order) {
        Sort sort = order.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        if (clubeId != null && estadioId != null) {
            return partidaRepository.findByClubeMandanteIdOrClubeVisitanteIdAndEstadioId(clubeId, clubeId, estadioId, pageable);
        } else if (clubeId != null) {
            return partidaRepository.findByClubeMandanteIdOrClubeVisitanteId(clubeId, clubeId, pageable);
        } else if (estadioId != null) {
            return partidaRepository.findByEstadioId(estadioId, pageable);
        } else {
            return partidaRepository.findAll(pageable);
        }
    }





}
