package com.salud.arqui.controller;

import com.salud.arqui.controller.dto.CitaMedicaDTO;
import com.salud.arqui.db.orm.CitaMedicaORM;
import com.salud.arqui.logica.CitaMedicaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@RestController
@AllArgsConstructor
public class CitasMedicasController {
    private CitaMedicaService citaMedicaService;

    List<CitaMedicaDTO> CitasMedicas = new ArrayList<>();

    @PostMapping(path = "/citaMedica")
    public String guardarCitaMedica(@RequestBody CitaMedicaDTO citaMedicaDTO) {
        citaMedicaService.guardarCitaMedica(citaMedicaDTO.tipoDeCita(), citaMedicaDTO.descripcion(), LocalDate.parse(citaMedicaDTO.fechaConsulta()), citaMedicaDTO.idAfiliado());
        return "Cita medica guardada con exito";
    }

    @GetMapping(path = "/citaMedica/todo")
    public List<CitaMedicaORM> obtenerTodasCitaMedicas() {
        return citaMedicaService.listaCitasMedicas();
    }

    @GetMapping(path = "/citaMedica/{id}")
    public ResponseEntity<CitaMedicaORM> obtenerCitaMedicaPorId(@PathVariable long id) {
        CitaMedicaORM citaMedica = citaMedicaService.getCitaMedicaById(id);
        if (citaMedica == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(citaMedica);
    }

    @DeleteMapping(path = "/citaMedica/{id}")
    public String eliminarCita(@PathVariable Long id) {
        citaMedicaService.eliminarCitaMedica(id);
        return "Cita medica eliminada con exito";
    }
}