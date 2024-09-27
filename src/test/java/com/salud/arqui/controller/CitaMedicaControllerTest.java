package com.salud.arqui.controller;

import com.salud.arqui.controller.dto.CitaMedicaDTO;
import com.salud.arqui.db.jpa.AfiliadoJPA;
import com.salud.arqui.db.jpa.BeneficiarioJPA;
import com.salud.arqui.db.jpa.CitaMedicaJPA;
import com.salud.arqui.db.jpa.HistorialMedicoJPA;
import com.salud.arqui.db.orm.AfiliadoORM;
import com.salud.arqui.db.orm.BeneficiarioORM;
import com.salud.arqui.db.orm.HistorialMedicoORM;
import com.salud.arqui.logica.CitaMedicaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;



public class CitaMedicaControllerTest {

    @Mock
    private CitaMedicaJPA citaMedicaJPA;

    @Mock
    private AfiliadoJPA afiliadoJPA;

    @Mock
    private BeneficiarioJPA beneficiarioJPA;

    @Mock
    private HistorialMedicoJPA historialMedicoJPA;

    @Mock
    private CitaMedicaService citaMedicaService;

    @InjectMocks
    private CitaMedicaController citaMedicaController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGuardarCitaMedicaAfiliado() {
        CitaMedicaDTO request = new CitaMedicaDTO("consulta", null, "Consulta", 1L, 1L);

        AfiliadoORM afiliadoORM = new AfiliadoORM();
        HistorialMedicoORM historialMedicoORM = new HistorialMedicoORM();
        historialMedicoORM.setIdHistorialMedico(1L);

        when(afiliadoJPA.findById(1L)).thenReturn(Optional.of(afiliadoORM));
        when(historialMedicoJPA.findByAfiliadoORM_idAfiliado(1L)).thenReturn(Optional.of(historialMedicoORM));

        ResponseEntity<String> response = citaMedicaController.guardarCitaMedica(request);

        assertEquals(ResponseEntity.ok("Cita médica guardada exitosamente."), response);
        verify(citaMedicaService).guardarCitaMedica("Consulta", "Revisión médica", LocalDate.parse("2024-09-17"), 1L, null, 1L);
    }

    @Test
    public void testGuardarCitaMedicaAfiliadoNoExiste() {
        CitaMedicaDTO request = new CitaMedicaDTO("consulta", null, "Consulta", 1L, 1L);

        when(afiliadoJPA.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<String> response = citaMedicaController.guardarCitaMedica(request);

        assertEquals(ResponseEntity.badRequest().body("El ID del afiliado no existe."), response);
    }


}
