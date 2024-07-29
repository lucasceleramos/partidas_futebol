package com.meli.partidas_futebol.service;
import com.meli.partidas_futebol.Exception.ValidationException;
import com.meli.partidas_futebol.dto.ClubeDto;
import com.meli.partidas_futebol.model.Clube;
import com.meli.partidas_futebol.repository.ClubeRepository;
import com.meli.partidas_futebol.repository.PartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service

    public class ClubeService {

    @Autowired
    private ClubeRepository clubeRepository;

    @Autowired
    private PartidaRepository partidaRepository;

    private static final List<String> ESTADOS_BRASIL = Arrays.asList ("AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO");

    public Clube cadastrarClube(ClubeDto clubeDto) {
        if (clubeDto.getNome() == null || clubeDto.getNome().length() < 2 )
            throw new ValidationException("Nome do clube é obrigatório e deve ter no mínimo 2 caracteres");

        if (!ESTADOS_BRASIL.contains(clubeDto.getSiglaEstado()))
            throw new ValidationException("Sigla do estado inválida");

        if (clubeDto.getFundacao() == null || clubeDto.getFundacao().isAfter(LocalDate.now()))
            throw new ValidationException("Data de criação não pode ser no futuro.");

        if (!clubeDto.isAtividade()) {
            throw new ValidationException("A atividade do clube é obrigatória!");
        }

        if (clubeRepository.existsByNomeAndSiglaEstado(clubeDto.getNome(), clubeDto.getSiglaEstado())){
            throw new ValidationException("Já existe um clube com esse nome no Estado.");
        }

        Clube clube = new Clube();
        clube.setNome(clubeDto.getNome());
        clube.setSiglaEstado(clubeDto.getSiglaEstado());
        clube.setFundacao(clubeDto.getFundacao());
        clube.setAtividade(clubeDto.isAtividade());

        return clubeRepository.save(clube);
    }

    public ClubeDto buscarClube(Long id) {
        Clube clube = clubeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Clube não encontrado com ID: " + id));
        return new ClubeDto(clube.getNome(), clube.getSiglaEstado(), clube.getFundacao(), clube.isAtividade());
    }

    public void editarClube(Long id, ClubeDto clubeDto) {
        Clube clube = clubeRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Clube não encontrado com ID: " + id));

        if (clubeDto.getNome().length() < 2 ){
            throw new ValidationException("Nome do clube é obrigatório e deve ter no mínimo 2 caracteres");
        }

        if (!ESTADOS_BRASIL.contains(clubeDto.getSiglaEstado())){
            throw new ValidationException("Sigla do estado inválida");
        }

        if (clubeDto.getFundacao().isAfter(LocalDate.now())){
            throw new ValidationException("Data de criação não pode ser no futuro.");
        }

        if (partidaRepository.existsByClubeMandanteOrClubeVisitanteAndDataHoraAfter(clube, clube, clubeDto.getFundacao().atStartOfDay())){
            throw new ValidationException("Data de criação não pode ser posterior a alguma partida do clube.");
        }

        if (clubeRepository.existsByNomeAndSiglaEstado(clubeDto.getNome(), clubeDto.getSiglaEstado())){
            throw new ValidationException("Já existe um clube com esse nome no Estado.");
        }

        clube.setNome(clubeDto.getNome());
        clube.setSiglaEstado(clubeDto.getSiglaEstado());
        clube.setFundacao(clubeDto.getFundacao());
        clube.setAtividade(clubeDto.isAtividade());
        clubeRepository.save(clube);
    }

    public void inativarClube(Long id) {
        Clube clube = clubeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Clube não encontrado com ID: " + id));
        clube.setAtividade(false);
        clubeRepository.save(clube);
    }

    public List<ClubeDto> listarClube(String nome, String siglaEstado, Boolean atividade, int page, int size, String sortBy, String order) {
        Pageable paging = PageRequest.of(page, size, Sort.Direction.fromString(order), sortBy);

        List<Clube> clubes;
        if (nome != null && siglaEstado != null && atividade != null) {
            clubes = clubeRepository.findByNomeContainingAndSiglaEstadoAndAtividade(nome, siglaEstado, atividade, paging);
        } else if (nome != null) {
            clubes = clubeRepository.findByNomeContaining(nome, paging);
        } else if (siglaEstado != null) {
            clubes = clubeRepository.findBySiglaEstado(siglaEstado, paging);
        } else if (atividade != null) {
            clubes = clubeRepository.findByAtividade(atividade, paging);
        } else {
            clubes = clubeRepository.findAll(paging).getContent();
        }

        if (clubes.isEmpty()) {
            return new ArrayList<>();
        }

        return clubes.stream().map(clube -> new ClubeDto(clube.getNome(), clube.getSiglaEstado(), clube.getFundacao(), clube.isAtividade())).collect(Collectors.toList());
    }


}
