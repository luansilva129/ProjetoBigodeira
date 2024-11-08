package br.com.atlas.bigodeira.backend.service;

import static org.junit.jupiter.api.Assertions.*;

import br.com.atlas.bigodeira.backend.domainBase.AgendamentoBase;
import br.com.atlas.bigodeira.backend.repository.AgendamentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AgendamentoServiceTest {

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @InjectMocks
    private AgendamentoService agendamentoService;

    @Test
    public void testFindAllAgendamentos() {
        AgendamentoBase agendamento1 = new AgendamentoBase();
        AgendamentoBase agendamento2 = new AgendamentoBase();
        List<AgendamentoBase> mockAgendamentos = Arrays.asList(agendamento1, agendamento2);

        when(agendamentoRepository.findAll()).thenReturn(mockAgendamentos);

        List<AgendamentoBase> result = agendamentoService.findAllAgendamentos();

        assertEquals(2, result.size(), "O tamanho da lista deve ser 2");
        assertEquals(mockAgendamentos, result, "A lista retornada deve ser igual à lista mockada");
    }

}