package br.com.atlas.bigodeira.backend.controller.cliente;


import br.com.atlas.bigodeira.backend.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ClienteController {

    @Autowired
    ClienteService clienteService = new ClienteService();

    public boolean autenticar(String email, String senha) {
        return clienteService.autenticar(email, senha);
    }


}
