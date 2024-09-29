package com.salud.arqui.integration;


import com.salud.arqui.controller.dto.AfiliadoDTO;
import com.salud.arqui.db.orm.AfiliadoORM;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "h2")
@Transactional
class AfiliadoIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void setUp() {
        AfiliadoDTO afiliadoInicial = new AfiliadoDTO("Juan Lopez", 40, "juan@mail.com", "M");
        testRestTemplate.postForEntity("/afiliado", afiliadoInicial, String.class);
    }

    @Test
    void When_crearAfiliado_Then_afiliadoGuardado() {
        AfiliadoDTO nuevoAfiliado = new AfiliadoDTO("Carlos Sanchez", 30, "carlos@mail.com", "Masculino");
        ResponseEntity<String> respuestaInsercion = testRestTemplate.postForEntity("/afiliado", nuevoAfiliado, String.class);
        Assertions.assertEquals("afiliado guardado con exito", respuestaInsercion.getBody());
    }

    @Test
    void When_actualizarAfiliado_Then_afiliadoActualizado() {

        AfiliadoDTO afiliadoInicial = new AfiliadoDTO("Carlos Sanchez", 30, "carlos@mail.com", "M");
        testRestTemplate.postForEntity("/afiliado", afiliadoInicial, String.class);

        AfiliadoORM afiliadoActualizado = new AfiliadoORM();
        afiliadoActualizado.setNombre("Carlos Perez");
        afiliadoActualizado.setEdad(35);
        afiliadoActualizado.setEmail("carlosperez@mail.com");
        afiliadoActualizado.setGenero("M");


        ResponseEntity<AfiliadoORM> respuesta = testRestTemplate.exchange("/afiliado/1", HttpMethod.PUT, new HttpEntity<>(afiliadoActualizado), AfiliadoORM.class);

        Assertions.assertNotNull(respuesta.getBody());
        Assertions.assertEquals("Carlos Perez", respuesta.getBody().getNombre());
        Assertions.assertEquals(35, respuesta.getBody().getEdad());
        Assertions.assertEquals("carlosperez@mail.com", respuesta.getBody().getEmail());
    }

    @Test
    void When_actualizarAfiliadoInexistente_Then_notFoundRetornado() {

        Long idInexistente = 999L;
        AfiliadoORM afiliadoActualizado = new AfiliadoORM();
        afiliadoActualizado.setNombre("Juan Perez");
        afiliadoActualizado.setEdad(40);
        afiliadoActualizado.setEmail("juanperez@mail.com");
        afiliadoActualizado.setGenero("M");

        ResponseEntity<AfiliadoORM> respuesta = testRestTemplate.exchange("/afiliado/" + idInexistente,HttpMethod.PUT,new HttpEntity<>(afiliadoActualizado),
                AfiliadoORM.class);


        Assertions.assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }


    @Test
    @Transactional
    void When_eliminarAfiliado_Then_afiliadoEliminado() {

        AfiliadoDTO afiliadoInicial = new AfiliadoDTO("sergio  sss", 25, "sergi@test.com", "M");
        testRestTemplate.postForEntity("/afiliado", afiliadoInicial, String.class);

        testRestTemplate.delete("/afiliado/4");

        ResponseEntity<AfiliadoORM> respuesta = testRestTemplate.getForEntity("/afiliado/4", AfiliadoORM.class);
        Assertions.assertEquals(HttpStatus.OK, respuesta.getStatusCode());
    }


    @Test
    void TraerAfiliados(){
        ResponseEntity<List> afiliados = testRestTemplate.getForEntity("/afiliado/todos", List.class);
        Assertions.assertFalse(Objects.requireNonNull(afiliados.getBody().isEmpty()));
    }

    @Test
    void When_traerAfiliados_Then_listaNoVacia() {
        ResponseEntity<List> afiliados = testRestTemplate.getForEntity("/afiliado/todos", List.class);
        Assertions.assertFalse(Objects.requireNonNull(afiliados.getBody()).isEmpty());
    }

    @Test
    void When_traerAfiliados_Then_listaVacia() {

        ResponseEntity<List> afiliados = testRestTemplate.getForEntity("/afiliado/todos", List.class);
        Assertions.assertFalse(Objects.requireNonNull(afiliados.getBody()).isEmpty());
    }

    @Test
    void When_buscarAfiliadoPorId_Then_afiliadoRetornado() {

        ResponseEntity<AfiliadoORM> responseBuscar = testRestTemplate.getForEntity("/afiliado/1", AfiliadoORM.class);


        Assertions.assertEquals(HttpStatus.OK, responseBuscar.getStatusCode());
        AfiliadoORM afiliadoEncontrado = responseBuscar.getBody();
        Assertions.assertNotNull(afiliadoEncontrado);
        Assertions.assertEquals("Carlos Perez", afiliadoEncontrado.getNombre());
        Assertions.assertEquals(35, afiliadoEncontrado.getEdad());
        Assertions.assertEquals("carlosperez@mail.com", afiliadoEncontrado.getEmail());
        Assertions.assertEquals("M", afiliadoEncontrado.getGenero());
    }

    @Test
    void When_buscarAfiliadoPorIdInexistente_Then_notFoundRetornado() {
        Long idInexistente = 999L;
        ResponseEntity<AfiliadoORM> responseBuscar = testRestTemplate.getForEntity("/afiliado/" + idInexistente, AfiliadoORM.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseBuscar.getStatusCode());
    }
}
