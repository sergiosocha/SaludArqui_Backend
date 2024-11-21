package com.salud.arqui.logica;

import com.salud.arqui.db.jpa.AfiliadoJPA;
import com.salud.arqui.db.jpa.BeneficiarioJPA;
import com.salud.arqui.db.jpa.HistorialMedicoJPA;
import com.salud.arqui.db.orm.AfiliadoORM;
import com.salud.arqui.db.orm.BeneficiarioORM;
import com.salud.arqui.db.orm.HistorialMedicoORM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



class HistorialMedicoServiceTest {

    @Mock
    private HistorialMedicoJPA historialMedicoJPA;

    @Mock
    private AfiliadoJPA afiliadoJPA;

    @Mock
    private BeneficiarioJPA beneficiarioJPA;

    @InjectMocks
    private HistorialMedicoService historialMedicoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearHistorialParaAfiliado() {

        Long idAfiliado = 1L;
        AfiliadoORM afiliadoORM = new AfiliadoORM();

        when(afiliadoJPA.findById(idAfiliado)).thenReturn(Optional.of(afiliadoORM));


        HistorialMedicoORM resultado = historialMedicoService.crearHistorialParaAfiliado(idAfiliado);


        assertNotNull(resultado);
        assertEquals(afiliadoORM, resultado.getAfiliadoORM());
        verify(historialMedicoJPA, times(1)).save(any(HistorialMedicoORM.class));
    }

    @Test
    void crearHistorialParaAfiliado_conAfiliadoNoExistente() {
        Long idAfiliado = 2L;

        when(afiliadoJPA.findById(idAfiliado)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            historialMedicoService.crearHistorialParaAfiliado(idAfiliado);
        });

        assertEquals("El afiliado no existe", exception.getMessage());
    }

    @Test
    void crearHistorialParaBeneficiario() {
        Long idBeneficiario = 3L;
        BeneficiarioORM beneficiarioORM = new BeneficiarioORM();

        when(beneficiarioJPA.findById(idBeneficiario)).thenReturn(Optional.of(beneficiarioORM));

        HistorialMedicoORM resultado = historialMedicoService.crearHistorialParaBeneficiario(idBeneficiario);

        assertNotNull(resultado);
        assertEquals(beneficiarioORM, resultado.getBeneficiarioORM());
        verify(historialMedicoJPA, times(1)).save(any(HistorialMedicoORM.class));
    }

    @Test
    void crearHistorialParaBeneficiario_conBeneficiarioNoExistente() {
        Long idBeneficiario = 4L;


        when(beneficiarioJPA.findById(idBeneficiario)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            historialMedicoService.crearHistorialParaBeneficiario(idBeneficiario);
        });

        assertEquals("El beneficiario no existe", exception.getMessage());
    }

    @Test
    void buscarHistorialMedicoPorAfiliado() {
        Long idAfiliado = 10L;
        HistorialMedicoORM historial = new HistorialMedicoORM();


        when(historialMedicoJPA.findByAfiliadoORM_idAfiliado(idAfiliado)).thenReturn(Optional.of(historial));

        HistorialMedicoORM resultado = historialMedicoService.buscarHistorialMedicoPorAfiliado(idAfiliado);

        assertNotNull(resultado);
        assertEquals(10L, idAfiliado);
    }


    @Test
    void buscarHistorialMedicoPorBeneficiario() {
        Long idBeneficiario = 11L;
        HistorialMedicoORM historial = new HistorialMedicoORM();

        when(historialMedicoJPA.findByBeneficiarioORM_idBeneficiario(idBeneficiario)).thenReturn(Optional.of(historial));

        HistorialMedicoORM resultado = historialMedicoService.buscarHistorialMedicoPorBeneficiario(idBeneficiario);

        assertNotNull(resultado);
        assertEquals(11L, idBeneficiario);
    }


}