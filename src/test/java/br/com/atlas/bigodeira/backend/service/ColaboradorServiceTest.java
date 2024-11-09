package br.com.atlas.bigodeira.backend.service;

import br.com.atlas.bigodeira.backend.domainBase.domain.Colaborador;
import br.com.atlas.bigodeira.backend.repository.ColaboradorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ColaboradorServiceTest {

    @Mock
    private ColaboradorRepository colaboradorRepository;

    @InjectMocks
    private ColaboradorService colaboradorService;

    private Colaborador colaborador1;
    private Colaborador colaborador2;

    @BeforeEach
    void setUp() {
        colaborador1 = new Colaborador();
        colaborador1.setId(1L);
        colaborador1.setNome("João");

        colaborador2 = new Colaborador();
        colaborador2.setId(2L);
        colaborador2.setNome("Maria");
    }

    @Test
    void testFindAll() {
        when(colaboradorRepository.findAll()).thenReturn(Arrays.asList(colaborador1, colaborador2));

        List<Colaborador> colaboradores = colaboradorService.findAll();

        assertEquals(2, colaboradores.size());
        assertEquals("João", colaboradores.get(0).getNome());
        assertEquals("Maria", colaboradores.get(1).getNome());
    }

    @Test
    void testCount() {
        when(colaboradorRepository.count()).thenReturn(2L);

        String count = colaboradorService.count();

        assertEquals("2", count);
    }
}