package br.com.atlas.bigodeira.backend.service;


import br.com.atlas.bigodeira.backend.domainBase.AgendamentoBase;
import br.com.atlas.bigodeira.backend.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendamentoService {

    @Autowired
    AgendamentoRepository agendamentoRepository;

    @Transactional
    public AgendamentoBase salvarAgendamento(AgendamentoBase agendamento) {
        return agendamentoRepository.save(agendamento);
    }

    public List<AgendamentoBase> findAllAgendamentos() {
        return agendamentoRepository.findAll();
    }

    public void deleteByClienteId(Long clienteId) {
        List<AgendamentoBase> agendamentos = agendamentoRepository.findByClienteId(clienteId);
        agendamentoRepository.deleteAll(agendamentos);
    }

    public List<AgendamentoBase> findByClienteId(Long clienteId) {
        return agendamentoRepository.findByClienteId(clienteId);
    }

    public List<AgendamentoBase> filterByCliente(String clienteName) {
        List<String> statuses = Arrays.asList("CONFIRMADO", "CANCELADO");
        return agendamentoRepository.findByClienteNomeContainingIgnoreCaseAndStatusIn(clienteName, statuses);
    }

    public List<AgendamentoBase> filterByColaborador(String colaboradorName) {
        List<String> statuses = Arrays.asList("CONFIRMADO", "CANCELADO");
        return agendamentoRepository.findByColaboradorNomeContainingIgnoreCaseAndStatusIn(colaboradorName, statuses);
    }


    public List<AgendamentoBase> filterByStatus(String status) {
        if ("TUDO".equals(status) || status.isEmpty()) {
            return agendamentoRepository.findAll();
        } else {
            return agendamentoRepository.findAll().stream()
                    .filter(agendamento -> agendamento.getStatus().equals(status))
                    .collect(Collectors.toList());
        }
    }

    public List<AgendamentoBase> filterAgendamentos(String clienteName, String colaboradorName, String status) {
        if (clienteName != null && !clienteName.isEmpty()) {
            clienteName = "%" + clienteName + "%";  // Para uso no banco com LIKE
        }
        if (colaboradorName != null && !colaboradorName.isEmpty()) {
            colaboradorName = "%" + colaboradorName + "%";  // Para uso no banco com LIKE
        }
        if (status != null && !status.isEmpty()) {
            status = "%" + status + "%";  // Para uso no banco com LIKE
        }

        return agendamentoRepository.filterAgendamentos(clienteName, colaboradorName, status);
    }



}
