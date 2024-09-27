package com.salud.arqui.controller;

import com.salud.arqui.db.jpa.AfiliadoJPA;
import com.salud.arqui.db.jpa.HistorialMedicoJPA;
import com.salud.arqui.db.orm.AfiliadoORM;
import com.salud.arqui.db.orm.HistorialMedicoORM;
import com.salud.arqui.logica.AfiliadoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AfiliadoControllerTest {

    @Mock
    private AfiliadoJPA afiliadoJPA;

    @Mock
    private HistorialMedicoJPA historialMedicoJPA;

    @InjectMocks
    private AfiliadoService afiliadoService;

    private AfiliadoORM afiliadoORM;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        afiliadoORM = new AfiliadoORM();
        afiliadoORM.setNombre("Juan Pérez");
        afiliadoORM.setEdad(30);
        afiliadoORM.setEmail("juan.perez@example.com");
        afiliadoORM.setGenero("Masculino");
    }

    @Test
    void guardarAfiliado_creaAfiliadoYHistorialCorrectamente() {
        String nombre = "Juan Pérez";
        Integer edad = 30;
        String email = "juan.perez@example.com";
        String genero = "Masculino";

        boolean result = afiliadoService.guardarAfiliado(nombre, edad, email, genero);

        verify(afiliadoJPA, times(1)).save(any(AfiliadoORM.class));
        verify(historialMedicoJPA, times(1)).save(any(HistorialMedicoORM.class));
        assertTrue(result);
    }

    @Test
    void listaAfiliados_devuelveListaDeAfiliados() {
        when(afiliadoJPA.findAll()).thenReturn(Collections.singletonList(afiliadoORM));

        var result = afiliadoService.listaAfiliados();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(afiliadoJPA, times(1)).findAll();
    }

    @Test
    void eliminarAfiliado_eliminaAfiliadoCorrectamente() {
        Long id = 1L;

        afiliadoService.eliminarAfiliado(id);

        verify(afiliadoJPA, times(1)).deleteById(id);
    }

    @Test
    void buscarAfiliadoId_devuelveAfiliadoExistente() {
        Long id = 1L;
        when(afiliadoJPA.findById(id)).thenReturn(Optional.of(afiliadoORM));

        AfiliadoORM result = afiliadoService.buscarAfiliadoId(id);

        assertNotNull(result);
        assertEquals("Juan Pérez", result.getNombre());
        verify(afiliadoJPA, times(1)).findById(id);
    }

    @Test
    void buscarAfiliadoId_noDevuelveAfiliadoSiNoExiste() {
        Long id = 1L;
        when(afiliadoJPA.findById(id)).thenReturn(Optional.empty());

        AfiliadoORM result = afiliadoService.buscarAfiliadoId(id);

        assertNull(result);
        verify(afiliadoJPA, times(1)).findById(id);
    }

    @Test
    void actualizarAfiliado_actualizaAfiliadoExistente() {
        Long id = 1L;
        when(afiliadoJPA.findById(id)).thenReturn(Optional.of(afiliadoORM));

        boolean result = afiliadoService.actualizarAfiliado(id, "Juan Actualizado", 31, "nuevo.email@example.com", "Masculino");

        assertTrue(result);
        verify(afiliadoJPA, times(1)).save(afiliadoORM);
    }

    @Test
    void actualizarAfiliado_noActualizaSiNoExiste() {
        Long id = 1L;

        // Simular que no se encuentra el afiliado
        when(afiliadoJPA.findById(id)).thenReturn(Optional.empty());

        // Llamar al método que estamos probando
        boolean result = afiliadoService.actualizarAfiliado(id, "Juan Actualizado", 31, "nuevo.email@example.com", "Masculino");

        // Comprobar que el resultado es falso cuando el afiliado no existe
        assertFalse(result, "Se esperaba que resultara falso ya que el afiliado no existe");

        // Verificar que el método save nunca fue llamado
        verify(afiliadoJPA, never()).save(any(AfiliadoORM.class));
    }

    @Test
    void guardarAfiliado() {
    }

    @Test
    void obtenerAfiliados() {
    }

    @Test
    void eliminarAfiliado() {
    }

    @Test
    void obtenerAfiliadoId() {
    }

    @Test
    void actualizarAfiliado() {
    }
}