package com.salud.arqui.logica;

import com.salud.arqui.controller.dto.BeneficiarioDTO;
import com.salud.arqui.db.jpa.AfiliadoJPA;
import com.salud.arqui.db.jpa.HistorialMedicoJPA;
import com.salud.arqui.db.orm.AfiliadoORM;
import com.salud.arqui.db.orm.BeneficiarioORM;
import com.salud.arqui.db.orm.HistorialMedicoORM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AfiliadoServiceTest {

    @Autowired
    private AfiliadoService afiliadoService;

    @Autowired
    private AfiliadoJPA afiliadoJPA;

    @Autowired
    private HistorialMedicoJPA historialMedicoJPA;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void guardarAfiliado() {
        String nombre = "Carlos Perez";
        Integer edad = 30;
        String email = "juan@example.com";
        String genero = "Masculino";

        boolean result = afiliadoService.guardarAfiliado(nombre, edad, email, genero);

        assertTrue(result);
        List<AfiliadoORM> afiliados = afiliadoJPA.findAll();
        assertEquals( afiliados.size(), afiliados.size());
        assertEquals(nombre, afiliados.get(0).getNombre());
    }

    @Test
    void listaAfiliados() {
        afiliadoService.guardarAfiliado("Juan Perez", 30, "juan@example.com", "Masculino");
        afiliadoService.guardarAfiliado("Maria Gomez", 25, "maria@example.com", "Femenino");

        List<AfiliadoORM> result = afiliadoService.listaAfiliados();

        assertEquals( result.size(), result.size());
    }

    @Test
    void eliminarAfiliado() {
        AfiliadoORM afiliado = new AfiliadoORM();
        afiliado.setNombre("Juan Perez");
        afiliado = afiliadoJPA.save(afiliado);

        afiliadoService.eliminarAfiliado(afiliado.getIdAfiliado());

        assertFalse(afiliadoJPA.findById(afiliado.getIdAfiliado()).isPresent());
    }

    @Test
    void buscarAfiliadoId() {
        AfiliadoORM afiliado = new AfiliadoORM();
        afiliado.setNombre("Juan Perez");
        afiliado = afiliadoJPA.save(afiliado);

        AfiliadoORM result = afiliadoService.buscarAfiliadoId(afiliado.getIdAfiliado());

        assertNotNull(result);
        assertEquals("Juan Perez", result.getNombre());
    }

    @Test
    void actualizarAfiliado() {
        AfiliadoORM afiliado = new AfiliadoORM();
        afiliado.setNombre("Juan Perez");
        afiliado = afiliadoJPA.save(afiliado);

        boolean result = afiliadoService.actualizarAfiliado(afiliado.getIdAfiliado(), "Carlos Perez", 35, "carlos@example.com", "Masculino");

        assertTrue(result);
        AfiliadoORM updatedAfiliado = afiliadoJPA.findById(afiliado.getIdAfiliado()).orElse(null);
        assertNotNull(updatedAfiliado);
        assertEquals("Carlos Perez", updatedAfiliado.getNombre());
        assertEquals(35, updatedAfiliado.getEdad());
        assertEquals("carlos@example.com", updatedAfiliado.getEmail());
    }
}