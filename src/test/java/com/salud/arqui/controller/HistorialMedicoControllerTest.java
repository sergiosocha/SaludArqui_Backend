package com.salud.arqui.controller;

import com.salud.arqui.controller.HistorialMedicoController;
import com.salud.arqui.db.orm.HistorialMedicoORM;
import com.salud.arqui.logica.HistorialMedicoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class HistorialMedicoControllerTest {

    @Mock
    private HistorialMedicoService historialMedicoService;

    @InjectMocks
    private HistorialMedicoController historialMedicoController;

    @Test
    public void testObtenerHistorialMedicoPorAfiliado_Existe() {

        long idAfiliado = 1L;
        HistorialMedicoORM historialMedicoORM = new HistorialMedicoORM();
        historialMedicoORM.setIdHistorialMedico(1L);


        when(historialMedicoService.buscarHistorialMedicoPorAfiliado(idAfiliado)).thenReturn(historialMedicoORM);

        ResponseEntity<HistorialMedicoORM> response = historialMedicoController.obtenerHistorialMedicoPorAfiliado(idAfiliado);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(historialMedicoORM.getIdHistorialMedico(), response.getBody().getIdHistorialMedico());


        verify(historialMedicoService).buscarHistorialMedicoPorAfiliado(idAfiliado);
    }

    @Test
    public void testObtenerHistorialMedicoPorAfiliado_NoExiste() {

        long idAfiliado = 1L;

        when(historialMedicoService.buscarHistorialMedicoPorAfiliado(idAfiliado)).thenReturn(null);

        ResponseEntity<HistorialMedicoORM> response = historialMedicoController.obtenerHistorialMedicoPorAfiliado(idAfiliado);


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());


        verify(historialMedicoService).buscarHistorialMedicoPorAfiliado(idAfiliado);
    }

    @Test
    public void testObtenerHistorialMedicoPorBeneficiario_Existe() {

        long idBeneficiario = 2L;
        HistorialMedicoORM historialMedicoORM = new HistorialMedicoORM();
        historialMedicoORM.setIdHistorialMedico(1L);


        when(historialMedicoService.buscarHistorialMedicoPorBeneficiario(idBeneficiario)).thenReturn(historialMedicoORM);


        ResponseEntity<HistorialMedicoORM> response = historialMedicoController.obtenerHistorialMedicoPorBeneficiario(idBeneficiario);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(historialMedicoORM.getIdHistorialMedico(), response.getBody().getIdHistorialMedico());


        verify(historialMedicoService).buscarHistorialMedicoPorBeneficiario(idBeneficiario);
    }

    @Test
    public void testObtenerHistorialMedicoPorBeneficiario_NoExiste() {

        long idBeneficiario = 2L;


        when(historialMedicoService.buscarHistorialMedicoPorBeneficiario(idBeneficiario)).thenReturn(null);


        ResponseEntity<HistorialMedicoORM> response = historialMedicoController.obtenerHistorialMedicoPorBeneficiario(idBeneficiario);


        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());


        verify(historialMedicoService).buscarHistorialMedicoPorBeneficiario(idBeneficiario);
    }
}
