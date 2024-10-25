package br.com.atlas.bigodeira.backend.service;

import br.com.atlas.bigodeira.backend.domainBase.AcessoBase;
import br.com.atlas.bigodeira.backend.repository.AcessosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcessosService {

    @Autowired
    AcessosRepository acessosRepository;

    public List<AcessoBase> findAll() { return acessosRepository.findAll(); }

    public void salvarAcesso(AcessoBase acesso) { acessosRepository.save(acesso); }

    public void deletarAcessos() { acessosRepository.deleteAll(); }

}
