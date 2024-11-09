package br.com.atlas.bigodeira.backend.service;

import br.com.atlas.bigodeira.backend.domainBase.AgendamentoBase;
import br.com.atlas.bigodeira.backend.repository.AgendamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AgendamentoServiceTest {

    @Mock
    private AgendamentoRepository agendamentoRepository;

    @InjectMocks
    private AgendamentoService agendamentoService;

    private AgendamentoBase agendamento;

    @BeforeEach
    void setUp() {
        agendamento = new AgendamentoBase();
        agendamento.setId(1L);
        agendamento.setNome("Teste Agendamento");
    }

    @Test
    void salvarAgendamento_deveRetornarAgendamentoSalvo() {
        when(agendamentoRepository.save(agendamento)).thenReturn(agendamento);

        AgendamentoBase resultado = agendamentoService.salvarAgendamento(agendamento);

        assertEquals(agendamento, resultado);
        verify(agendamentoRepository).save(agendamento);
    }

    @Test
    void testFindAllAgendamentos() {
        AgendamentoBase agendamento1 = new AgendamentoBase();
        AgendamentoBase agendamento2 = new AgendamentoBase();
        List<AgendamentoBase> mockAgendamentos = Arrays.asList(agendamento1, agendamento2);

        when(agendamentoRepository.findAll()).thenReturn(mockAgendamentos);

        List<AgendamentoBase> result = agendamentoService.findAllAgendamentos();

        assertEquals(2, result.size());
        assertEquals(mockAgendamentos, result);
    }

    @Test
    void deleteByClienteId_deveDeletarAgendamentosDoCliente() {
        Long clienteId = 100L;
        List<AgendamentoBase> mockAgendamentos = Arrays.asList(agendamento);

        when(agendamentoRepository.findByClienteId(clienteId)).thenReturn(mockAgendamentos);

        agendamentoService.deleteByClienteId(clienteId);

        verify(agendamentoRepository).findByClienteId(clienteId);
        verify(agendamentoRepository).deleteAll(mockAgendamentos);
    }

    @Test
    void findByClienteId_deveRetornarListaDeAgendamentos() {
        Long clienteId = 100L;
        List<AgendamentoBase> mockAgendamentos = Arrays.asList(agendamento);

        when(agendamentoRepository.findByClienteId(clienteId)).thenReturn(mockAgendamentos);

        List<AgendamentoBase> result = agendamentoService.findByClienteId(clienteId);

        assertEquals(mockAgendamentos, result);
    }

    @Test
    void filterByCliente_deveRetornarAgendamentosPorNomeClienteEStatus() {
        String clienteName = "Teste";
        List<String> statuses = Arrays.asList("CONFIRMADO", "CANCELADO");
        List<AgendamentoBase> mockAgendamentos = Collections.singletonList(agendamento);

        when(agendamentoRepository.findByClienteNomeContainingIgnoreCaseAndStatusIn(clienteName, statuses))
                .thenReturn(mockAgendamentos);

        List<AgendamentoBase> result = agendamentoService.filterByCliente(clienteName);

        assertEquals(mockAgendamentos, result);
    }

    @Test
    void filterByColaborador_deveRetornarAgendamentosPorNomeColaboradorEStatus() {
        String colaboradorName = "Colaborador";
        List<String> statuses = Arrays.asList("CONFIRMADO", "CANCELADO");
        List<AgendamentoBase> mockAgendamentos = Collections.singletonList(agendamento);

        when(agendamentoRepository.findByColaboradorNomeContainingIgnoreCaseAndStatusIn(colaboradorName, statuses))
                .thenReturn(mockAgendamentos);

        List<AgendamentoBase> result = agendamentoService.filterByColaborador(colaboradorName);

        assertEquals(mockAgendamentos, result);
    }

    @Test
    void filterByStatus_deveRetornarAgendamentosFiltradosPorStatus() {
        String status = "CONFIRMADO";
        AgendamentoBase agendamento = new AgendamentoBase();
        agendamento.setStatus("CONFIRMADO");
        // agendamento.setStatus("CANCELADO");

        List<AgendamentoBase> mockAgendamentos = Arrays.asList(agendamento);

        when(agendamentoRepository.findAll()).thenReturn(mockAgendamentos);

        List<AgendamentoBase> result = agendamentoService.filterByStatus(status);

        assertEquals(mockAgendamentos, result);
    }

    @Test
    void filterAgendamentos_deveRetornarAgendamentosFiltradosPorParametros() {
        String clienteName = "TesteCliente";
        String colaboradorName = "TesteColaborador";
        String status = "CONFIRMADO";
        List<AgendamentoBase> mockAgendamentos = Arrays.asList(agendamento);

        when(agendamentoRepository.filterAgendamentos("%" + clienteName + "%", "%" + colaboradorName + "%", "%" + status + "%"))
                .thenReturn(mockAgendamentos);

        List<AgendamentoBase> result = agendamentoService.filterAgendamentos(clienteName, colaboradorName, status);

        assertEquals(mockAgendamentos, result);
    }

    @Test
    void count_deveRetornarQuantidadeAgendamentosConfirmados() {
        when(agendamentoRepository.countByStatus("CONFIRMADO")).thenReturn(10L);

        String result = agendamentoService.count();

        assertEquals("10", result);
    }

    @Test
    void VerificaAguardando_deveRetornarAgendamentosAguardando() {
        when(agendamentoRepository.agendamentosAguardando("AGUARDANDO")).thenReturn("5");

        String result = agendamentoService.VerificaAguardando();

        assertEquals("5", result);
    }
}