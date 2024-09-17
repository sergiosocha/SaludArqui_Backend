package com.salud.arqui.controller;


import com.salud.arqui.controller.dto.BeneficiarioDTO;
import com.salud.arqui.db.orm.AfiliadoORM;
import com.salud.arqui.db.orm.BeneficiarioORM;
import com.salud.arqui.logica.AfiliadoService;
import com.salud.arqui.logica.BeneficiarioService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class BeneficiarioController {

    private BeneficiarioService beneficiarioService;


    @PostMapping(path = "/beneficiario")
    public String guardarBeneficiario(@RequestBody BeneficiarioDTO beneficiarioDTO){
        beneficiarioService.guardarBeneficiario(beneficiarioDTO.nombre(),beneficiarioDTO.email(),beneficiarioDTO.idAfiliado());
        return "Se ha registrado el beneficiario correctamente";
    }

    @GetMapping(path = "/beneficiario/todos")
    public List<BeneficiarioORM> obtenerBeneficiarios(){
        return beneficiarioService.listaBeneficiario();
    }

    @DeleteMapping(path = "/beneficiario/{id}")
    public String eliminarBeneficiario (@PathVariable Long id){
        beneficiarioService.eliminarBeneficiario(id);
        return  "Se ha eliminado el beneficiario exiosamente";
    }

    @GetMapping(path = "beneficiario/{id}")
    public ResponseEntity<BeneficiarioORM> obtenerBeneficiarioId(@PathVariable Long id){
        BeneficiarioORM beneficiario = beneficiarioService.buscarBeneficiarioId(id);
        if(beneficiario == null)
            log.info("No se encontro el beneficiario Solicitado con id: " + id);
        return ResponseEntity.ok(beneficiario);
    }

    @PutMapping(path = "/beneficiario/{id}")
    public ResponseEntity<BeneficiarioORM> actualizarBeneficiario( @PathVariable Long id, @RequestBody BeneficiarioDTO beneficiarioDTO) {

        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        boolean actualizado = beneficiarioService.actualizarBeneficiario(id, beneficiarioDTO);

        if (!actualizado) {
            return ResponseEntity.notFound().build();
        }

        BeneficiarioORM beneficiarioActualizado = beneficiarioService.buscarBeneficiarioId(id);

        return ResponseEntity.ok(beneficiarioActualizado);
    }




}
