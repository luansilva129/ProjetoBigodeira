package br.com.atlas.bigodeira.backend.controller.cliente;


import br.com.atlas.bigodeira.backend.domainBase.AgendamentoBase;
import br.com.atlas.bigodeira.backend.domainBase.domain.Cliente;
import br.com.atlas.bigodeira.backend.service.AgendamentoService;
import br.com.atlas.bigodeira.backend.service.AuthService;
import br.com.atlas.bigodeira.backend.service.ClienteService;
import com.vaadin.flow.component.UI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @Autowired
    private AgendamentoService agendamentoService;

    @Autowired
    private AuthService authService;

    public boolean autenticar(String email, String senha) {
        return clienteService.autenticar(email, senha);
    }
    public Optional<Cliente> findClienteByEmail(String email) {
        return Optional.ofNullable(clienteService.findByEmail(email));
    }

    public List<AgendamentoBase> getAgendamentosCliente(Long clienteId) {
        return agendamentoService.findByClienteId(clienteId);
    }

    public boolean isClienteLogado() {
        return authService.isClienteLogado();
    }

    public void logout() {
        AuthService.logout();
        UI.getCurrent().navigate("/cliente/home");
    }



}
