package br.com.atlas.bigodeira.backend.service;


import br.com.atlas.bigodeira.backend.domainBase.AgendamentoBase;
import br.com.atlas.bigodeira.backend.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    public void confirmarAgendamento(Long id) {
        AgendamentoBase agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
        agendamento.setStatus(true);
        agendamentoRepository.save(agendamento);
    }

    public void cancelarAgendamento(Long id) {
        AgendamentoBase agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
        agendamento.setStatus(false);
        agendamentoRepository.save(agendamento);
    }
}
