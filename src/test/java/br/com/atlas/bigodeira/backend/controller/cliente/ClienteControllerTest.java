package br.com.atlas.bigodeira.backend.controller.cliente;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

import br.com.atlas.bigodeira.backend.domainBase.domain.Cliente;
import br.com.atlas.bigodeira.backend.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void autenticar_deveRetornarTrueQuandoCredenciaisEstaoCorretas() {

        String email = "email@exemplo.com";
        String senha = "senhaSegura";

        when(clienteService.autenticar(email, senha)).thenReturn(true);

        boolean autenticado = clienteController.autenticar(email, senha);

        assertTrue(autenticado);
    }

    @Test
    void autenticar_deveRetornarFalseQuandoCredenciaisEstaoIncorretas() {
        String email = "erro@example.com";
        String senha = "senhaIncorreta";

        when(clienteService.autenticar(email, senha)).thenReturn(false);

        boolean autenticado = clienteController.autenticar(email, senha);

        assertFalse(autenticado);
    }
    @Test
    void findClienteByEmail_deveRetornarClienteQuandoEmailExiste() {
        String email = "email@exemplo.com";
        Cliente cliente = new Cliente();
        cliente.setEmail(email);

        when(clienteService.findByEmail(email)).thenReturn(cliente);

        Optional<Cliente> resultado = clienteController.findClienteByEmail(email);

        assertTrue(resultado.isPresent());
        assertEquals(cliente, resultado.get());
    }

    @Test
    void findClienteByEmail_deveRetornarOptionalEmptyQuandoEmailNaoExiste() {
        String email = "erro@example.com";

        when(clienteService.findByEmail(email)).thenReturn(null);

        Optional<Cliente> resultado = clienteController.findClienteByEmail(email);

        assertTrue(resultado.isEmpty());
    }


}