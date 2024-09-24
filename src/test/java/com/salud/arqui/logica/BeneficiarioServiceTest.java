package com.salud.arqui.logica;

import com.salud.arqui.db.orm.AfiliadoORM;
import com.salud.arqui.db.orm.BeneficiarioORM;
import com.salud.arqui.db.jpa.AfiliadoJPA;
import com.salud.arqui.db.jpa.BeneficiarioJPA;
import com.salud.arqui.controller.dto.BeneficiarioDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class BeneficiarioServiceTest {


    @Mock
    private BeneficiarioJPA beneficiarioJPA;

    @Mock
    private AfiliadoJPA afiliadoJPA;

    @InjectMocks
    private BeneficiarioService beneficiarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void guardarBeneficiario() {
        String nombre = "Juan Pérez";
        String email = "juan.perez@example.com";
        Long idAfiliado = 1L;

        AfiliadoORM afiliadoORM = new AfiliadoORM();

        when(afiliadoJPA.findById(idAfiliado)).thenReturn(Optional.of(afiliadoORM));
        when(beneficiarioJPA.findByAfliliadoORM(afiliadoORM)).thenReturn(Optional.empty());

        boolean result = beneficiarioService.guardarBeneficiario(nombre, email, idAfiliado);

        assertTrue(result);
        verify(beneficiarioJPA, times(1)).save(any(BeneficiarioORM.class));
    }

    @Test
    void guardarBeneficiarioAfiliadoNoExistente() {
        Long idAfiliado = 1L;

        when(afiliadoJPA.findById(idAfiliado)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            beneficiarioService.guardarBeneficiario("Nombre", "email@example.com", idAfiliado);
        });

        assertEquals("El afiliado con id " + idAfiliado + " no existe", exception.getMessage());
        verify(beneficiarioJPA, times(0)).save(any(BeneficiarioORM.class));
    }

    @Test
    void listaBeneficiario() {
        BeneficiarioORM beneficiarioORM = new BeneficiarioORM();
        when(beneficiarioJPA.findAll()).thenReturn(List.of(beneficiarioORM));

        List<BeneficiarioORM> result = beneficiarioService.listaBeneficiario();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(beneficiarioJPA, times(1)).findAll();
    }

    @Test
    void eliminarBeneficiario() {
        Long idBeneficiario = 1L;

        doNothing().when(beneficiarioJPA).deleteById(idBeneficiario);

        beneficiarioService.eliminarBeneficiario(idBeneficiario);

        verify(beneficiarioJPA, times(1)).deleteById(idBeneficiario);
    }

    @Test
    void buscarBeneficiarioId() {

        Long idBeneficiario = 1L;
        BeneficiarioORM beneficiarioORM = new BeneficiarioORM();


        when(beneficiarioJPA.findById(idBeneficiario)).thenReturn(Optional.of(beneficiarioORM));

        BeneficiarioORM result = beneficiarioService.buscarBeneficiarioId(idBeneficiario);

        assertNotNull(result);
        assertEquals(idBeneficiario, idBeneficiario);
        verify(beneficiarioJPA, times(1)).findById(idBeneficiario);
    }

    @Test
    void actualizarBeneficiario() {
        Long idBeneficiario = 1L;
        Long idAfiliado = 2L;
        BeneficiarioORM beneficiarioORM = new BeneficiarioORM();
        AfiliadoORM afiliadoORM = new AfiliadoORM();

        BeneficiarioDTO beneficiarioDTO = new BeneficiarioDTO("Juan Pérez", "juan.perez@example.com", idAfiliado);

        when(beneficiarioJPA.findById(idBeneficiario)).thenReturn(Optional.of(beneficiarioORM));
        when(afiliadoJPA.findById(idAfiliado)).thenReturn(Optional.of(afiliadoORM));

        boolean result = beneficiarioService.actualizarBeneficiario(idBeneficiario, beneficiarioDTO);

        assertTrue(result);
        verify(beneficiarioJPA, times(1)).save(any(BeneficiarioORM.class));
    }

    @Test
    void actualizarBeneficiarioNoExistente() {
        Long idBeneficiario = 1L;
        BeneficiarioDTO beneficiarioDTO = new BeneficiarioDTO("Juan Pérez", "juan.perez@example.com", null);

        when(beneficiarioJPA.findById(idBeneficiario)).thenReturn(Optional.empty());

        boolean result = beneficiarioService.actualizarBeneficiario(idBeneficiario, beneficiarioDTO);

        assertFalse(result);
        verify(beneficiarioJPA, times(0)).save(any(BeneficiarioORM.class));
    }
}