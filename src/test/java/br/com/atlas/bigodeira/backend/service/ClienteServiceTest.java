package br.com.atlas.bigodeira.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import br.com.atlas.bigodeira.backend.domainBase.AgendamentoBase;
import br.com.atlas.bigodeira.backend.domainBase.domain.Cliente;
import br.com.atlas.bigodeira.backend.repository.AgendamentoRepository;
import br.com.atlas.bigodeira.backend.repository.ClienteRepository;
import br.com.atlas.bigodeira.backend.service.ClienteService;
import br.com.atlas.bigodeira.backend.service.AgendamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;
    private AgendamentoService agendamentoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void autenticar_deveRetornarTrueQuandoCredenciaisEstaoCorretas() {
        String email = "email@exemplo.com";
        String senha = "senhaSegura";
        Cliente cliente = new Cliente();
        cliente.setEmail(email);
        cliente.setSenha(senha);

        when(clienteRepository.findByEmail(email)).thenReturn(Optional.of(cliente));

        boolean autenticado = clienteService.autenticar(email, senha);

        assertTrue(autenticado);
    }

    @Test
    void autenticar_deveRetornarFalseQuandoSenhaEstaIncorreta() {
        String email = "email@exemplo.com";
        String senhaCorreta = "senha123";
        String senhaIncorreta = "senhaErrada";
        Cliente cliente = new Cliente();
        cliente.setEmail(email);
        cliente.setSenha(senhaCorreta);

        when(clienteRepository.findByEmail(email)).thenReturn(Optional.of(cliente));

        boolean autenticado = clienteService.autenticar(email, senhaIncorreta);

        assertFalse(autenticado);
    }

    @Test
    void autenticar_deveRetornarFalseQuandoEmailNaoExiste() {
        String email = "erro@example.com";
        String senha = "senhaSegura";

        when(clienteRepository.findByEmail(email)).thenReturn(Optional.empty());

        boolean autenticado = clienteService.autenticar(email, senha);

        assertFalse(autenticado);
    }

    @Test
    void findByEmail_deveRetornarClienteQuandoEmailExiste() {

        String email = "email@exemplo.com";
        Cliente cliente = new Cliente();
        cliente.setEmail(email);

        when(clienteRepository.findByEmail(email)).thenReturn(Optional.of(cliente));


        Cliente resultado = clienteService.findByEmail(email);


        assertEquals(cliente, resultado);
    }

    @Test
    void findByEmail_deveRetornarNullQuandoEmailNaoExiste() {

        String email = "erro@example.com";

        when(clienteRepository.findByEmail(email)).thenReturn(Optional.empty());


        Cliente resultado = clienteService.findByEmail(email);


        assertNull(resultado);
    }

}
