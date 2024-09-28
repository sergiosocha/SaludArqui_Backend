package com.salud.arqui.integration;


import com.salud.arqui.controller.dto.AfiliadoDTO;
import com.salud.arqui.controller.dto.CitaMedicaDTO;
import com.salud.arqui.db.jpa.AfiliadoJPA;
import com.salud.arqui.db.jpa.BeneficiarioJPA;
import com.salud.arqui.db.jpa.CitaMedicaJPA;
import com.salud.arqui.db.jpa.HistorialMedicoJPA;
import com.salud.arqui.db.orm.AfiliadoORM;
import com.salud.arqui.db.orm.BeneficiarioORM;
import com.salud.arqui.db.orm.HistorialMedicoORM;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "h2")
public class CitaMedicaIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AfiliadoJPA afiliadoJPA;

    @Autowired
    private BeneficiarioJPA beneficiarioJPA;

    @Autowired
    private HistorialMedicoJPA historialMedicoJPA;

    @Autowired
    private CitaMedicaJPA citaMedicaJPA;


    @Test
    public void When_guardarCitaMedicaWithValidAfiliado_Then_citaMedicaGuardada() {

        AfiliadoORM afiliado = new AfiliadoORM();
        afiliado.setNombre("Juan Perez");
        afiliadoJPA.save(afiliado);

        HistorialMedicoORM historial = new HistorialMedicoORM();
        historial.setAfiliadoORM(afiliado);
        historialMedicoJPA.save(historial);

        CitaMedicaDTO citaMedicaDTO = new CitaMedicaDTO(
                "General", "Revisión anual", "2024-09-28", afiliado.getIdAfiliado(), null
        );


        ResponseEntity<String> response = testRestTemplate.postForEntity(
                "/citaMedica", citaMedicaDTO, String.class
        );

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Cita médica guardada exitosamente.", response.getBody());
    }


    @Test
    public void  When_guardarCitaMedicaAfiliadoNoExistente_Then_returnBadRequest() {
        CitaMedicaDTO citaMedicaDTO = new CitaMedicaDTO("General", "Revisión anual", "2024-09-28", 999L, null);
        ResponseEntity<String> response = testRestTemplate.postForEntity("/citaMedica", citaMedicaDTO, String.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("El ID del afiliado no existe.", response.getBody());
    }


    @Test
    public void When_guardarCitaMedicaSinAfiliadoNiBeneficiario_Then_returnBadRequest() {

        CitaMedicaDTO citaMedicaDTO = new CitaMedicaDTO(
                "General", "Revisión anual", "2024-09-28", null, null
        );


        ResponseEntity<String> response = testRestTemplate.postForEntity(
                "/citaMedica", citaMedicaDTO, String.class
        );


        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Debe proporcionar al menos un ID de afiliado o beneficiario.", response.getBody());
    }


    @Test
    @Transactional
    public void when_GuardarCitaMedicaBeneficiario() {
        AfiliadoDTO afiliadoInicial = new AfiliadoDTO("Juan Lopez", 40, "juan@mail.com", "M");
        ResponseEntity<String> responseAfiliado = testRestTemplate.postForEntity("/afiliado", afiliadoInicial, String.class);


        Assertions.assertEquals(HttpStatus.OK, responseAfiliado.getStatusCode());


        AfiliadoORM afiliado1 = afiliadoJPA.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("Afiliado no encontrado"));

        AfiliadoDTO afiliado2 = new AfiliadoDTO("Juan Lopez", 40, "juan@mail.com", "M");
        ResponseEntity<String> responseAfiliado2 = testRestTemplate.postForEntity("/afiliado", afiliado2, String.class);


        Assertions.assertEquals(HttpStatus.OK, responseAfiliado2.getStatusCode());


        AfiliadoORM afiliados2 = afiliadoJPA.findById(2L)
                .orElseThrow(() -> new IllegalArgumentException("Afiliado no encontrado"));


        BeneficiarioORM beneficiario = new BeneficiarioORM();
        beneficiario.setNombre("Maria Gomez");
        beneficiario.setAfliliadoORM(afiliados2);
        beneficiario = beneficiarioJPA.save(beneficiario);


        HistorialMedicoORM historial = new HistorialMedicoORM();
        historial.setAfiliadoORM(afiliados2);
        historialMedicoJPA.save(historial);


        CitaMedicaDTO citaMedicaDTO = new CitaMedicaDTO(
                "Odontología", "Revisión dental", "2024-09-28", afiliado1.getIdAfiliado(), beneficiario.getIdBeneficiario()
        );

        ResponseEntity<String> response = testRestTemplate.postForEntity(
                "/citaMedica", citaMedicaDTO, String.class
        );


        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("El ID del beneficiario no existe o ya está asignado a otro afiliado.", response.getBody());
    }
}