package br.com.atlas.bigodeira.backend.controller.colaborador;

import br.com.atlas.bigodeira.backend.domainBase.domain.Colaborador;
import br.com.atlas.bigodeira.backend.repository.ColaboradorRepository;
import br.com.atlas.bigodeira.backend.service.ColaboradorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class CadastrarColaboradorController {

    private final ColaboradorRepository colaboradorRepository;

    public CadastrarColaboradorController(ColaboradorRepository colaboradorRepository) {
        this.colaboradorRepository = colaboradorRepository;
    }

    @GetMapping("/colaboradores")
    public List<Colaborador> getColaboradores() {
        ColaboradorService colaboradorService = new ColaboradorService();
        return colaboradorService.findAll();
    }

    public Colaborador salvarColaborador (Colaborador colaborador){
        return colaboradorRepository.save(colaborador);
    }

    public void deleteColaborador (Colaborador colaborador){
        if (colaboradorRepository != null){
            colaboradorRepository.delete(colaborador);
        }
    }
}
