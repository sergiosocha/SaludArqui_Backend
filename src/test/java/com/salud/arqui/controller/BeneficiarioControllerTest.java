package com.salud.arqui.controller;
import com.salud.arqui.controller.dto.BeneficiarioDTO;
import com.salud.arqui.db.orm.BeneficiarioORM;
import com.salud.arqui.logica.BeneficiarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;



public class BeneficiarioControllerTest {

    @Mock
    private BeneficiarioService beneficiarioService;

    @InjectMocks
    private BeneficiarioController beneficiarioController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGuardarBeneficiario() {
        BeneficiarioDTO beneficiarioDTO = new BeneficiarioDTO("Juan", "juan@email.com", 1L);
        String response = beneficiarioController.guardarBeneficiario(beneficiarioDTO);

        verify(beneficiarioService).guardarBeneficiario("Juan", "juan@email.com", 1L);
        assertEquals("Se ha registrado el beneficiario correctamente", response);
    }

    @Test
    public void testObtenerBeneficiarios() {
        List<BeneficiarioORM> beneficiarios = Arrays.asList(new BeneficiarioORM(), new BeneficiarioORM());

        when(beneficiarioService.listaBeneficiario()).thenReturn(beneficiarios);

        List<BeneficiarioORM> result = beneficiarioController.obtenerBeneficiarios();

        assertEquals(beneficiarios, result);
        verify(beneficiarioService).listaBeneficiario();
    }

    @Test
    public void testEliminarBeneficiario() {
        String response = beneficiarioController.eliminarBeneficiario(1L);

        verify(beneficiarioService).eliminarBeneficiario(1L);
        assertEquals("Se ha eliminado el beneficiario exiosamente", response);
    }

    @Test
    public void testObtenerBeneficiarioId() {
        BeneficiarioORM beneficiario = new BeneficiarioORM();

        when(beneficiarioService.buscarBeneficiarioId(1L)).thenReturn(beneficiario);

        ResponseEntity<BeneficiarioORM> response = beneficiarioController.obtenerBeneficiarioId(1L);

        assertEquals(ResponseEntity.ok(beneficiario), response);
        verify(beneficiarioService).buscarBeneficiarioId(1L);
    }

    @Test
    public void testActualizarBeneficiario() {
        BeneficiarioDTO beneficiarioDTO = new BeneficiarioDTO("Nuevo Nombre", "nuevo@email.com", 1L);

        when(beneficiarioService.actualizarBeneficiario(1L, beneficiarioDTO)).thenReturn(true);
        BeneficiarioORM beneficiarioORM = new BeneficiarioORM();
        when(beneficiarioService.buscarBeneficiarioId(1L)).thenReturn(beneficiarioORM);

        ResponseEntity<BeneficiarioORM> response = beneficiarioController.actualizarBeneficiario(1L, beneficiarioDTO);

        assertEquals(ResponseEntity.ok(beneficiarioORM), response);
        verify(beneficiarioService).actualizarBeneficiario(1L, beneficiarioDTO);
    }
}
