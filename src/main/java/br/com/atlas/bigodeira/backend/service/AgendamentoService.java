package br.com.atlas.bigodeira.backend.service;


import br.com.atlas.bigodeira.backend.domainBase.AgendamentoBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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


    public void cancelarAgendamento(Long id) {
        Optional<AgendamentoBase> optionalAgendamento = agendamentoRepository.findById(id);
        if (optionalAgendamento.isPresent()) {
            AgendamentoBase agendamento = optionalAgendamento.get();
            agendamento.setStatus(false);
            agendamentoRepository.save(agendamento);
        } else {
            throw new RuntimeException("Agendamento não encontrado com id: " + id);
        }
    }

    public void confirmarAgendamento(Long id) {
        AgendamentoBase agendamento = agendamentoRepository.findById(id).get();
        agendamento.setStatus(true);
    }
}
