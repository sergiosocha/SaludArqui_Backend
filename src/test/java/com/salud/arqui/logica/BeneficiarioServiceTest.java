package com.salud.arqui.logica;

import com.salud.arqui.controller.dto.BeneficiarioDTO;
import com.salud.arqui.db.jpa.AfiliadoJPA;
import com.salud.arqui.db.jpa.BeneficiarioJPA;
import com.salud.arqui.db.orm.AfiliadoORM;
import com.salud.arqui.db.orm.BeneficiarioORM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BeneficiarioServiceTest {

    @Mock
    private BeneficiarioJPA beneficiarioJPA;

    @Mock
    private AfiliadoJPA afiliadoJPA;

    @InjectMocks
    private BeneficiarioService beneficiarioService;

    private AfiliadoORM afiliadoORM;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        afiliadoORM = new AfiliadoORM();

    }

    @Test
    void guardarBeneficiario_creaBeneficiarioCorrectamente() {
        String nombre = "Juan Pérez";
        String email = "juan.perez@example.com";
        Long idAfiliado = 1L;

        when(afiliadoJPA.findById(idAfiliado)).thenReturn(Optional.of(afiliadoORM));

        boolean result = beneficiarioService.guardarBeneficiario(nombre, email, idAfiliado);

        assertTrue(result);
        verify(beneficiarioJPA, times(1)).save(any(BeneficiarioORM.class));
    }

    @Test
    void guardarBeneficiario_afiliadoNoExistente_lanzaExcepcion() {
        String nombre = "Juan Pérez";
        String email = "juan.perez@example.com";
        Long idAfiliado = 1L;

        when(afiliadoJPA.findById(idAfiliado)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                beneficiarioService.guardarBeneficiario(nombre, email, idAfiliado));

        assertEquals("1 no existe", exception.getMessage());
    }

    @Test
    void listaBeneficiario_devuelveLista() {
        List<BeneficiarioORM> lista = beneficiarioService.listaBeneficiario();

        assertNotNull(lista);
        verify(beneficiarioJPA, times(1)).findAll();
    }

    @Test
    void eliminarBeneficiario_eliminaBeneficiarioCorrectamente() {
        Long id = 1L;

        beneficiarioService.eliminarBeneficiario(id);

        verify(beneficiarioJPA, times(1)).deleteById(id);
    }

    @Test
    void buscarBeneficiarioId_devuelveBeneficiario() {
        Long id = 1L;
        BeneficiarioORM beneficiarioORM = new BeneficiarioORM();
        when(beneficiarioJPA.findById(id)).thenReturn(Optional.of(beneficiarioORM));

        BeneficiarioORM result = beneficiarioService.buscarBeneficiarioId(id);

        assertNotNull(result);
        verify(beneficiarioJPA, times(1)).findById(id);
    }

    @Test
    void buscarBeneficiarioId_noExisteBeneficiario_devuelveNull() {
        Long id = 1L;
        when(beneficiarioJPA.findById(id)).thenReturn(Optional.empty());

        BeneficiarioORM result = beneficiarioService.buscarBeneficiarioId(id);

        assertNull(result);
        verify(beneficiarioJPA, times(1)).findById(id);
    }

    @Test
    void actualizarBeneficiario_actualizaBeneficiarioCorrectamente() {
        Long id = 1L;
        BeneficiarioDTO beneficiarioDTO = new BeneficiarioDTO("Juan Pérez", "juan.perez@example.com", null);
        BeneficiarioORM beneficiarioORM = new BeneficiarioORM();
        when(beneficiarioJPA.findById(id)).thenReturn(Optional.of(beneficiarioORM));

        boolean result = beneficiarioService.actualizarBeneficiario(id, beneficiarioDTO);

        assertTrue(result);
        verify(beneficiarioJPA, times(1)).save(beneficiarioORM);
    }

    @Test
    void actualizarBeneficiario_noExisteBeneficiario_devuelveFalse() {
        Long id = 1L;
        BeneficiarioDTO beneficiarioDTO = new BeneficiarioDTO("Juan Pérez", "juan.perez@example.com", null);
        when(beneficiarioJPA.findById(id)).thenReturn(Optional.empty());

        boolean result = beneficiarioService.actualizarBeneficiario(id, beneficiarioDTO);

        assertFalse(result);
        verify(beneficiarioJPA, never()).save(any(BeneficiarioORM.class));
    }
}