package com.salud.arqui.integration;

import com.salud.arqui.controller.dto.AfiliadoDTO;
import com.salud.arqui.controller.dto.BeneficiarioDTO;
import com.salud.arqui.controller.dto.HistorialMedicoDTO;
import com.salud.arqui.db.orm.HistorialMedicoORM;
import com.salud.arqui.logica.HistorialMedicoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "h2")
public class HistorialMedicoIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private HistorialMedicoService historialMedicoService;

    @Test
    void When_obtenerHistorialMedicoPorAfiliado_Then_historialEncontrado() {
        AfiliadoDTO afiliadoInicial = new AfiliadoDTO("Juan Lopez", 40, "juan@mail.com", "M");
        testRestTemplate.postForEntity("/afiliado", afiliadoInicial, String.class);

        Long idAfiliado = 1L;

        ResponseEntity<HistorialMedicoDTO> response = testRestTemplate.getForEntity(
                "/historialMedico/afiliado/{idAfiliado}", HistorialMedicoDTO.class, idAfiliado);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void When_obtenerHistorialMedicoPorAfiliadoInexistente_Then_historialNoEncontrado() {
        long idAfiliadoInexistente = 999L;

        ResponseEntity<HistorialMedicoORM> response = testRestTemplate.getForEntity(
                "/historialMedico/afiliado/{idAfiliado}", HistorialMedicoORM.class, idAfiliadoInexistente);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void When_obtenerHistorialMedicoPorBeneficiarioInexistente_Then_historialNoEncontrado() {
        long idBeneficiarioInexistente = 999L;

        ResponseEntity<HistorialMedicoORM> response = testRestTemplate.getForEntity(
                "/historialMedico/beneficiario/{idBeneficiario}", HistorialMedicoORM.class, idBeneficiarioInexistente);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}