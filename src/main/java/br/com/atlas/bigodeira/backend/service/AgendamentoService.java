package br.com.atlas.bigodeira.backend.service;


import br.com.atlas.bigodeira.backend.domainBase.AgendamentoBase;
import br.com.atlas.bigodeira.backend.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

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



}
