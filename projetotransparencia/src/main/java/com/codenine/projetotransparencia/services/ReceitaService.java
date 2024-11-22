package com.codenine.projetotransparencia.services;

import com.codenine.projetotransparencia.entities.Receita;
import com.codenine.projetotransparencia.repository.ReceitasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReceitaService {

    @Autowired
    private ReceitasRepository receitasRepository;

    public List<Receita> listarReceitas() {
        return receitasRepository.findAll();
    }

    public List<Receita> listarReceitasPorAno(int ano) {
        return receitasRepository.findAll().stream()
                .filter(receita -> {
                    LocalDate localDate = receita.getData();
                    Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    return cal.get(Calendar.YEAR) == ano;
                })
                .collect(Collectors.toList());
    }

    public Optional<Receita> buscarReceitaPorId(Long id) {
        return receitasRepository.findById(id);
    }
}
