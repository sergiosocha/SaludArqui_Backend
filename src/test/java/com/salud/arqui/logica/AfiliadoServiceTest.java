package com.salud.arqui.logica;

import com.salud.arqui.db.jpa.AfiliadoJPA;
import com.salud.arqui.db.jpa.HistorialMedicoJPA;
import com.salud.arqui.db.orm.AfiliadoORM;
import com.salud.arqui.db.orm.HistorialMedicoORM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class AfiliadoServiceTest {

    @Mock
    private AfiliadoJPA afiliadoJPA;

    @Mock
    private HistorialMedicoJPA historialMedicoJPA;

    @InjectMocks
    private AfiliadoService afiliadoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void guardarAfiliado() {

        String nombre = "Juan Perez";
        Integer edad = 30;
        String email = "juan@example.com";
        String genero = "Masculino";


        AfiliadoORM nuevoAfiliado = new AfiliadoORM();
        nuevoAfiliado.setNombre(nombre);
        nuevoAfiliado.setEdad(edad);
        nuevoAfiliado.setEmail(email);
        nuevoAfiliado.setGenero(genero);

        when(afiliadoJPA.save(any(AfiliadoORM.class))).thenReturn(nuevoAfiliado);

        boolean result = afiliadoService.guardarAfiliado(nombre, edad, email, genero);

        verify(afiliadoJPA, times(1)).save(any(AfiliadoORM.class));
        verify(historialMedicoJPA, times(1)).save(any(HistorialMedicoORM.class));

        assertTrue(result);
    }

    @Test
    void listaAfiliados() {

        AfiliadoORM afiliado1 = new AfiliadoORM();
        afiliado1.setNombre("Juan Perez");

        AfiliadoORM afiliado2 = new AfiliadoORM();
        afiliado2.setNombre("Maria Gomez");

        List<AfiliadoORM> afiliados = Arrays.asList(afiliado1, afiliado2);

        when(afiliadoJPA.findAll()).thenReturn(afiliados);

        List<AfiliadoORM> result = afiliadoService.listaAfiliados();

        verify(afiliadoJPA, times(1)).findAll();
        assertEquals(2, result.size());
    }

    @Test
    void eliminarAfiliado() {
        Long idAfiliado = 1L;
        afiliadoService.eliminarAfiliado(idAfiliado);
        verify(afiliadoJPA, times(1)).deleteById(idAfiliado);
    }

    @Test
    void buscarAfiliadoId() {
        Long idAfiliado = 1L;
        AfiliadoORM afiliado = new AfiliadoORM();

        afiliado.setNombre("Juan Perez");

        when(afiliadoJPA.findById(idAfiliado)).thenReturn(Optional.of(afiliado));
        AfiliadoORM result = afiliadoService.buscarAfiliadoId(idAfiliado);


        verify(afiliadoJPA, times(1)).findById(idAfiliado);
        assertNotNull(result);
        assertEquals("Juan Perez", result.getNombre());
    }

    @Test
    void actualizarAfiliado() {
        Long idAfiliado = 1L;
        AfiliadoORM afiliadoExistente = new AfiliadoORM();
        afiliadoExistente.setNombre("Juan Perez");
        when(afiliadoJPA.findById(idAfiliado)).thenReturn(Optional.of(afiliadoExistente));

        boolean result = afiliadoService.actualizarAfiliado(idAfiliado, "Carlos Perez", 35, "carlos@example.com", "Masculino");

        verify(afiliadoJPA, times(1)).findById(idAfiliado);
        verify(afiliadoJPA, times(1)).save(any(AfiliadoORM.class));

        assertTrue(result);
        assertEquals("Carlos Perez", afiliadoExistente.getNombre());
        assertEquals(35, afiliadoExistente.getEdad());
        assertEquals("carlos@example.com", afiliadoExistente.getEmail());
    }
}