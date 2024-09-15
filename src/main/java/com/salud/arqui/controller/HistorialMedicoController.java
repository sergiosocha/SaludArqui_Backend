package com.salud.arqui.controller;


import com.salud.arqui.controller.dto.HistorialMedicoDTO;
import com.salud.arqui.logica.HistorialMedicoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController

public class HistorialMedicoController {

    private HistorialMedicoService historialMedicoService;

    List<HistorialMedicoDTO> HistorialMedico = new ArrayList<>();

    @PostMapping(path= "/HistorialMedico")
    public String guardarHistorialMedico(@RequestBody HistorialMedicoDTO historialMedicoDTO) {
        historialMedicoService.nuevoHistoriaMedica(historialMedicoDTO.tipoConsulta(), historialMedicoDTO.detalle());
        return "Historial medico guardado";
    }

    @GetMapping(path = "/historialMedico")
    public List<HistorialMedicoDTO> obtenerHistorialMedico() {
        return HistorialMedico;
    }

    @DeleteMapping(path = "/citaMedica/{id}")
    public String eliminarHistorialMedico(@PathVariable Long id) {
        historialMedicoService.eliminarHistorialMedico(id);
        return "Historial medico eliminado";
    }

}
