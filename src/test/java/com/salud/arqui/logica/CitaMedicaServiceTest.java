package com.salud.arqui.logica;

import com.salud.arqui.db.jpa.AfiliadoJPA;
import com.salud.arqui.db.jpa.BeneficiarioJPA;
import com.salud.arqui.db.jpa.CitaMedicaJPA;
import com.salud.arqui.db.jpa.HistorialMedicoJPA;
import com.salud.arqui.db.orm.AfiliadoORM;
import com.salud.arqui.db.orm.BeneficiarioORM;
import com.salud.arqui.db.orm.CitaMedicaORM;
import com.salud.arqui.db.orm.HistorialMedicoORM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



class CitaMedicaServiceTest {

    @Mock
    private CitaMedicaJPA citaMedicaJPA;

    @Mock
    private AfiliadoJPA afiliadoJPA;

    @Mock
    private BeneficiarioJPA beneficiarioJPA;

    @Mock
    private HistorialMedicoJPA historialMedicoJPA;

    @Mock
    private HistorialMedicoORM historialMedico;

    @InjectMocks
    private CitaMedicaService citaMedicaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void guardarCitaMedica() {
        Long idAfiliado = 1L;
        Long idBeneficiario = 2L;
        Long idHistorialMedico = 3L;
        String tipoDeCita = "Consulta General";
        String descripcion = "Descripción de prueba";
        LocalDate fechaConsulta = LocalDate.now();

        AfiliadoORM afiliado = new AfiliadoORM();
        BeneficiarioORM beneficiario = new BeneficiarioORM();


        when(historialMedicoJPA.findById(idHistorialMedico)).thenReturn(Optional.of(historialMedico));
        when(afiliadoJPA.findById(idAfiliado)).thenReturn(Optional.of(afiliado));
        when(beneficiarioJPA.findById(idBeneficiario)).thenReturn(Optional.of(beneficiario));

        List<CitaMedicaORM> citasMedicas = new ArrayList<>();

        when(historialMedico.getCitasMedicas()).thenReturn(citasMedicas);

        // Ejecuta el método
        boolean resultado = citaMedicaService.guardarCitaMedica(tipoDeCita, descripcion, fechaConsulta, idAfiliado, idBeneficiario, idHistorialMedico);

        assertTrue(resultado);

        verify(citaMedicaJPA, times(1)).save(any(CitaMedicaORM.class));
        verify(historialMedicoJPA, times(1)).save(historialMedico);
    }

    @Test
    void listaCitasMedicas() {

        CitaMedicaORM cita1 = new CitaMedicaORM();
        CitaMedicaORM cita2 = new CitaMedicaORM();
        List<CitaMedicaORM> listaCitas = Arrays.asList(cita1, cita2);

        when(citaMedicaJPA.findAll()).thenReturn(listaCitas);

        List<CitaMedicaORM> resultado = citaMedicaService.listaCitasMedicas();

        assertEquals(2, resultado.size());
        verify(citaMedicaJPA, times(1)).findAll();
    }

    @Test
    void eliminarCitaMedica() {

        Long idCita = 1L;

        citaMedicaService.eliminarCitaMedica(idCita);

        verify(citaMedicaJPA, times(1)).deleteById(idCita);
    }

    @Test
    void getCitaMedicaById() {
        Long idCita = 1L;
        CitaMedicaORM citaMedica = new CitaMedicaORM();

        when(citaMedicaJPA.findById(idCita)).thenReturn(Optional.of(citaMedica));

        CitaMedicaORM resultado = citaMedicaService.getCitaMedicaById(idCita);

        assertNotNull(resultado);
        verify(citaMedicaJPA, times(1)).findById(idCita);
    }
}