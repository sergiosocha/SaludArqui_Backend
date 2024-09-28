package com.salud.arqui.integration;

import com.salud.arqui.controller.dto.AfiliadoDTO;
import com.salud.arqui.controller.dto.BeneficiarioDTO;
import com.salud.arqui.db.orm.BeneficiarioORM;
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

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles(profiles = "h2")
@Transactional
public class BeneficiarioIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private Long afiliadoId;

    @BeforeEach
    void setUp() {

        afiliadoId = 1L;
    }

    @Test
    void When_guardarBeneficiario_Then_beneficiarioGuardado() {

        AfiliadoDTO afiliadoInicial = new AfiliadoDTO("Juan Lopez", 40, "juan@mail.com", "M");
        ResponseEntity<String> responseAfiliado = testRestTemplate.postForEntity("/afiliado", afiliadoInicial, String.class);


        Assertions.assertEquals(HttpStatus.OK, responseAfiliado.getStatusCode());

        afiliadoId = 1L;


        BeneficiarioDTO beneficiarioDTO = new BeneficiarioDTO("Pedro Perez", "pedro@mail.com", 1L);
        ResponseEntity<String> response = testRestTemplate.postForEntity("/beneficiario", beneficiarioDTO, String.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Se ha registrado el beneficiario correctamente", response.getBody());


        ResponseEntity<List> beneficiarios = testRestTemplate.getForEntity("/beneficiario/todos", List.class);
        Assertions.assertFalse(Objects.requireNonNull(beneficiarios.getBody()).isEmpty());
    }

    @Test
    void When_obtenerBeneficiarios_Then_listaNoVacia() {

        AfiliadoDTO afiliadoInicial = new AfiliadoDTO("Juan Lopez", 40, "juan@mail.com", "M");
        ResponseEntity<String> responseAfiliado = testRestTemplate.postForEntity("/afiliado", afiliadoInicial, String.class);


        Assertions.assertEquals(HttpStatus.OK, responseAfiliado.getStatusCode());

        afiliadoId = 1L;


        BeneficiarioDTO beneficiarioDTO = new BeneficiarioDTO("Pedro Perez", "pedro@mail.com", 1L);
        ResponseEntity<String> response = testRestTemplate.postForEntity("/beneficiario", beneficiarioDTO, String.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Se ha registrado el beneficiario correctamente", response.getBody());

        ResponseEntity<List> beneficiarios = testRestTemplate.getForEntity("/beneficiario/todos", List.class);
        Assertions.assertFalse(Objects.requireNonNull(beneficiarios.getBody()).isEmpty());
    }


    @Test
    void When_actualizarBeneficiario_Then_beneficiarioActualizado() {

        AfiliadoDTO afiliadoInicial = new AfiliadoDTO("Juan Lopez", 40, "juan@mail.com", "M");
        ResponseEntity<String> responseAfiliado = testRestTemplate.postForEntity("/afiliado", afiliadoInicial, String.class);
        Assertions.assertEquals(HttpStatus.OK, responseAfiliado.getStatusCode());


        BeneficiarioDTO beneficiarioDTO = new BeneficiarioDTO("Carlos", "carlos@mail.com", 1L);
        ResponseEntity<String> responseBeneficiario = testRestTemplate.postForEntity("/beneficiario", beneficiarioDTO, String.class);
        Assertions.assertEquals(HttpStatus.OK, responseBeneficiario.getStatusCode());


        BeneficiarioDTO nuevoBeneficiarioDTO = new BeneficiarioDTO("Carlos Actualizado", "carlosactualizado@mail.com", 1L);
        ResponseEntity<BeneficiarioORM> updateResponse = testRestTemplate.exchange(
                "/beneficiario/1", HttpMethod.PUT, new HttpEntity<>(nuevoBeneficiarioDTO), BeneficiarioORM.class
        );


        Assertions.assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        Assertions.assertEquals("Carlos Actualizado", updateResponse.getBody().getNombre());
    }

    @Test
    void When_obtenerBeneficiarioPorId_Then_beneficiarioEncontrado() {

        AfiliadoDTO afiliadoInicial = new AfiliadoDTO("Juan Lopez", 40, "juan@mail.com", "M");
        ResponseEntity<String> responseAfiliado = testRestTemplate.postForEntity("/afiliado", afiliadoInicial, String.class);


        Assertions.assertEquals(HttpStatus.OK, responseAfiliado.getStatusCode());

        BeneficiarioDTO beneficiarioDTO = new BeneficiarioDTO("Maria", "maria@mail.com", 1L);
        testRestTemplate.postForEntity("/beneficiario", beneficiarioDTO, String.class);


        ResponseEntity<BeneficiarioORM> response = testRestTemplate.getForEntity("/beneficiario/1", BeneficiarioORM.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Assertions.assertEquals("Maria", response.getBody().getNombre());
    }

    @Test
    void When_obtenerBeneficiarioInexistente_Then_beneficiarioNoEncontrado() {

        AfiliadoDTO afiliadoInicial = new AfiliadoDTO("Juan Lopez", 40, "juan@mail.com", "M");
        ResponseEntity<String> responseAfiliado = testRestTemplate.postForEntity("/afiliado", afiliadoInicial, String.class);


        Assertions.assertEquals(HttpStatus.OK, responseAfiliado.getStatusCode());

        BeneficiarioDTO beneficiarioDTO = new BeneficiarioDTO("Maria", "maria@mail.com", 1L);
        testRestTemplate.postForEntity("/beneficiario", beneficiarioDTO, String.class);

        ResponseEntity<BeneficiarioORM> response = testRestTemplate.getForEntity("/beneficiario/2", BeneficiarioORM.class);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
