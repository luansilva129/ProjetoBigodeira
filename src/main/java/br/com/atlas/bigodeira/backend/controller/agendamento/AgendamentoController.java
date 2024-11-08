package br.com.atlas.bigodeira.backend.controller.agendamento;


import br.com.atlas.bigodeira.backend.domainBase.AgendamentoBase;
import br.com.atlas.bigodeira.backend.service.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AgendamentoController {


    @Autowired
    private AgendamentoService agendamentoService;


    public List<AgendamentoBase> getAgendamentos() {
        return agendamentoService.findAllAgendamentos();
    }


    public List<AgendamentoBase> getAgendamentosByCliente(String clienteName) {
        return agendamentoService.filterByCliente(clienteName);
    }


    public List<AgendamentoBase> getAgendamentosByColaborador(String colaboradorName) {
        return agendamentoService.filterByColaborador(colaboradorName);
    }


    public List<AgendamentoBase> getAgendamentosByStatus(String status) {
        return agendamentoService.filterByStatus(status);
    }

    public List<AgendamentoBase> getFilteredAgendamentos(String clienteName, String colaboradorName, String status) {
        return agendamentoService.filterAgendamentos(clienteName, colaboradorName, status);
    }
}
