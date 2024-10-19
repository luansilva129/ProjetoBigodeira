package br.com.atlas.bigodeira.backend.controller.colaborador;

import br.com.atlas.bigodeira.backend.domainBase.domain.Colaborador;
import br.com.atlas.bigodeira.backend.repository.ColaboradorRepository;
import br.com.atlas.bigodeira.backend.service.ColaboradorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class ColaboradorController {

    private final ColaboradorRepository colaboradorRepository;

    public ColaboradorController(ColaboradorRepository colaboradorRepository) {
        this.colaboradorRepository = colaboradorRepository;
    }

    @GetMapping("/colaboradores")
    public List<Colaborador> getColaboradores() {
        ColaboradorService colaboradorService = new ColaboradorService();
        return colaboradorService.findAll();
    }

    public Colaborador save(Colaborador colaborador){
        return colaboradorRepository.save(colaborador);
    }

}
