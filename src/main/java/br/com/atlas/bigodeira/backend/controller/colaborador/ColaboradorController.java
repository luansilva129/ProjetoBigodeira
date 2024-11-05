package br.com.atlas.bigodeira.backend.controller.colaborador;


import br.com.atlas.bigodeira.backend.service.ColaboradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ColaboradorController {

    final
    ColaboradorService colaboradorService;


    public ColaboradorController(ColaboradorService colaboradorService) {
        this.colaboradorService = colaboradorService;
    }

}
