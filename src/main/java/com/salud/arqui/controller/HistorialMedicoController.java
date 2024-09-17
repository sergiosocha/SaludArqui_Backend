package com.salud.arqui.controller;


import com.salud.arqui.controller.dto.HistorialMedicoDTO;
import com.salud.arqui.db.orm.HistorialMedicoORM;
import com.salud.arqui.logica.HistorialMedicoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@AllArgsConstructor
public class HistorialMedicoController {

    private HistorialMedicoService historialMedicoService;

    List<HistorialMedicoDTO> HistorialMedico = new ArrayList<>();


    @GetMapping(path = "/historialMedico/todo")
    public ResponseEntity<List<HistorialMedicoORM>> obtenerHistorialesMedico() {
        List<HistorialMedicoORM> historialMedico = historialMedicoService.listarHistorialMedico();
        return  ResponseEntity.ok(historialMedico);
    }

    @GetMapping(path = "/historialMedico/{id}")
    public ResponseEntity<List<HistorialMedicoORM>>obtenerHistorialMedico(@PathVariable long id){
        List<HistorialMedicoORM> historialMedico = (List<HistorialMedicoORM>) historialMedicoService.buscarHistorialMedico(id);
        if (historialMedico == null) {
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(historialMedico);
    }

    /*@DeleteMapping(path = "/citaMedica/{id}")
    public String eliminarHistorialMedico(@PathVariable Long id) {
        historialMedicoService.eliminarHistorialMedico(id);
        return "Historial medico eliminado";
    }*/

}
