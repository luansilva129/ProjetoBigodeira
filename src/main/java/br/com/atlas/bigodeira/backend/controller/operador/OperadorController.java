package br.com.atlas.bigodeira.backend.controller.operador;


import br.com.atlas.bigodeira.backend.service.OperadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class OperadorController {

    @Autowired
    OperadorService operadorService;

    public boolean autenticarOperador(String email, String senha) {
        return operadorService.autenticarOperador(email, senha);
    }
}
