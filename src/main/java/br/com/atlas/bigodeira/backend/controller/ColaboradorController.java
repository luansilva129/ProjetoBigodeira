package br.com.atlas.bigodeira.backend.controller;


import br.com.atlas.bigodeira.backend.domainBase.domain.Colaborador;
import br.com.atlas.bigodeira.backend.service.ColaboradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ColaboradorController {

    @Autowired
    ColaboradorService colaboradorService;

    @GetMapping("/colaboradores")
    public List<Colaborador> getColaboradores() {
        return colaboradorService.findAll();
    }



}
