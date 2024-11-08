package br.com.atlas.bigodeira.backend.controller.agendamento;

import static org.junit.jupiter.api.Assertions.*;
import br.com.atlas.bigodeira.backend.domainBase.AgendamentoBase;
import br.com.atlas.bigodeira.backend.service.AgendamentoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AgendamentoControllerTest {

    @Mock
    private AgendamentoService agendamentoService;

    @InjectMocks
    private AgendamentoController agendamentoController;

    @Test
    public void testGetAgendamentos() {
        AgendamentoBase agendamento1 = new AgendamentoBase();
        AgendamentoBase agendamento2 = new AgendamentoBase();
        List<AgendamentoBase> mockAgendamentos = Arrays.asList(agendamento1, agendamento2);

        when(agendamentoService.findAllAgendamentos()).thenReturn(mockAgendamentos);

        List<AgendamentoBase> result = agendamentoController.getAgendamentos();

        assertEquals(2, result.size(), "O tamanho da lista deve ser 2");
        assertEquals(mockAgendamentos, result, "A lista retornada deve ser igual à lista mockada");
    }
}