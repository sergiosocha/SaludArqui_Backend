package com.salud.arqui.controller;

import com.salud.arqui.controller.dto.CitaMedicaDTO;
import com.salud.arqui.db.jpa.AfiliadoJPA;
import com.salud.arqui.db.jpa.BeneficiarioJPA;
import com.salud.arqui.db.jpa.CitaMedicaJPA;
import com.salud.arqui.db.jpa.HistorialMedicoJPA;
import com.salud.arqui.db.orm.AfiliadoORM;
import com.salud.arqui.db.orm.BeneficiarioORM;
import com.salud.arqui.db.orm.CitaMedicaORM;
import com.salud.arqui.db.orm.HistorialMedicoORM;
import com.salud.arqui.logica.CitaMedicaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@AllArgsConstructor
public class CitaMedicaController {
    private final CitaMedicaJPA citaMedicaJPA;
    private final AfiliadoJPA afiliadoJPA;
    private final BeneficiarioJPA beneficiarioJPA;
    private final HistorialMedicoJPA historialMedicoJPA;
    private final CitaMedicaService citaMedicaService;


    @PostMapping("/citaMedica")
    public ResponseEntity<String> guardarCitaMedica(@RequestBody CitaMedicaDTO request) {
        try {

            AfiliadoORM afiliado = null;
            if (request.idAfiliado() != null) {
                afiliado = afiliadoJPA.findById(request.idAfiliado()).orElseThrow(() ->
                        new IllegalArgumentException("El ID del afiliado no existe."));
            }

            BeneficiarioORM beneficiario = null;
            if (request.idBeneficiario() != null) {
                beneficiario = beneficiarioJPA.findById(request.idBeneficiario()).orElse(null);
            }


            HistorialMedicoORM historialMedico = null;
            if (afiliado != null) {
                historialMedico = historialMedicoJPA.findByAfiliadoORM_idAfiliado(request.idAfiliado())
                        .orElseThrow(() -> new IllegalArgumentException("No se encontró el historial médico para el afiliado con ID: " + request.idAfiliado()));
            } else if (beneficiario != null) {
                historialMedico = historialMedicoJPA.findByBeneficiarioORM_idBeneficiario(request.idBeneficiario())
                        .orElse(null);
            } else {
                return ResponseEntity.badRequest().body("Debe proporcionar al menos un ID de afiliado o beneficiario.");
            }

            citaMedicaService.guardarCitaMedica(
                    request.tipoDeCita(),
                    request.descripcion(),
                    LocalDate.parse(request.fechaConsulta()),
                    request.idAfiliado(),
                    request.idBeneficiario(),
                    historialMedico.getIdHistorialMedico()
            );

            return ResponseEntity.ok("Cita médica guardada exitosamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
