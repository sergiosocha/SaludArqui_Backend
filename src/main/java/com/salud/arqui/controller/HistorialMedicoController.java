package com.salud.arqui.controller;


import com.salud.arqui.db.orm.HistorialMedicoORM;
import com.salud.arqui.logica.HistorialMedicoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@AllArgsConstructor
public class HistorialMedicoController {
    private final HistorialMedicoService historialMedicoService;

    @GetMapping(path = "/historialMedico/afiliado/{idAfiliado}")
    public ResponseEntity<HistorialMedicoORM> obtenerHistorialMedicoPorAfiliado(@PathVariable long idAfiliado) {
        HistorialMedicoORM historial = historialMedicoService.buscarHistorialMedicoPorAfiliado(idAfiliado);
        if (historial == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(historial);
    }

    @GetMapping(path = "/historialMedico/beneficiario/{idBeneficiario}")
    public ResponseEntity<HistorialMedicoORM> obtenerHistorialMedicoPorBeneficiario(@PathVariable long idBeneficiario) {
        HistorialMedicoORM historial = historialMedicoService.buscarHistorialMedicoPorBeneficiario(idBeneficiario);
        if (historial == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(historial);
    }

}

