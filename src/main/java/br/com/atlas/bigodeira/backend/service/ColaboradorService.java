package br.com.atlas.bigodeira.backend.service;

import br.com.atlas.bigodeira.backend.domainBase.domain.Colaborador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColaboradorService {

    @Autowired
    ColaboradorRepository colaboradorRepository;

    public List<Colaborador> findAll() {
        return colaboradorRepository.findAll();
    }

    public void save(Colaborador colaborador) {
        colaboradorRepository.save(colaborador);
    }
}
