package br.com.atlas.bigodeira.backend.service;

import br.com.atlas.bigodeira.backend.domainBase.ServicosBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicosService {

    @Autowired
    ServicosRepository servicosRepository;

    public List<ServicosBase> findAll() {
        return servicosRepository.findAll();
    }

    public void save(ServicosBase servicosBase) {
        servicosRepository.save(servicosBase);
    }

    public void delete(Long id) { servicosRepository.deleteById(id); }

    public Optional<ServicosBase> findById(Long id) {
        return servicosRepository.findById(id);
    }

}
