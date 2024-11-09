package br.com.atlas.bigodeira.backend.controller.agendamento;

import br.com.atlas.bigodeira.backend.domainBase.AgendamentoBase;
import br.com.atlas.bigodeira.backend.service.AgendamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.anyString;

@WebMvcTest(AgendamentoController.class)
public class AgendamentoControllerTest {

    @Mock
    private AgendamentoService agendamentoService;

    @InjectMocks
    private AgendamentoController agendamentoController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(agendamentoController).build();
    }

    @Test
    public void testGetAgendamentos() throws Exception {
        AgendamentoBase agendamento1 = new AgendamentoBase();
        agendamento1.setStatus("CONFIRMADO");

        AgendamentoBase agendamento2 = new AgendamentoBase();
        agendamento2.setStatus("CANCELADO");

        List<AgendamentoBase> agendamentos = Arrays.asList(agendamento1, agendamento2);

        when(agendamentoService.findAllAgendamentos()).thenReturn(agendamentos);

        mockMvc.perform(get("/agendamentos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("CONFIRMADO"))
                .andExpect(jsonPath("$[1].status").value("CANCELADO"));
    }

    @Test
    public void testGetAgendamentosByCliente() throws Exception {
        AgendamentoBase agendamento = new AgendamentoBase();
        agendamento.setStatus("CONFIRMADO");
        List<AgendamentoBase> agendamentos = Arrays.asList(agendamento);

        when(agendamentoService.filterByCliente(anyString())).thenReturn(agendamentos);

        mockMvc.perform(get("/agendamentos/cliente")
                        .param("clienteName", "ClienteTeste"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("CONFIRMADO"));
    }

    @Test
    public void testGetAgendamentosByColaborador() throws Exception {
        AgendamentoBase agendamento = new AgendamentoBase();
        agendamento.setStatus("CANCELADO");
        List<AgendamentoBase> agendamentos = Arrays.asList(agendamento);

        when(agendamentoService.filterByColaborador(anyString())).thenReturn(agendamentos);

        mockMvc.perform(get("/agendamentos/colaborador")
                        .param("colaboradorName", "ColaboradorTeste"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("CANCELADO"));
    }

    @Test
    public void testGetAgendamentosByStatus() throws Exception {
        AgendamentoBase agendamento = new AgendamentoBase();
        agendamento.setStatus("CONFIRMADO");
        List<AgendamentoBase> agendamentos = Arrays.asList(agendamento);

        when(agendamentoService.filterByStatus(anyString())).thenReturn(agendamentos);

        mockMvc.perform(get("/agendamentos/status")
                        .param("status", "CONFIRMADO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("CONFIRMADO"));
    }

    @Test
    public void testGetFilteredAgendamentos() throws Exception {
        AgendamentoBase agendamento = new AgendamentoBase();
        agendamento.setStatus("CANCELADO");
        List<AgendamentoBase> agendamentos = Arrays.asList(agendamento);

        when(agendamentoService.filterAgendamentos(anyString(), anyString(), anyString())).thenReturn(agendamentos);

        mockMvc.perform(get("/agendamentos/filtrados")
                        .param("clienteName", "Cliente")
                        .param("colaboradorName", "Colaborador")
                        .param("status", "CANCELADO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("CANCELADO"));
    }
}