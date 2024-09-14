package com.salud.arqui.controller;


import com.salud.arqui.controller.dto.BeneficiarioDTO;
import com.salud.arqui.db.orm.AfiliadoORM;
import com.salud.arqui.db.orm.BeneficiarioORM;
import com.salud.arqui.logica.AfiliadoService;
import com.salud.arqui.logica.BeneficiarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
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
            System.out.println("No se encontro el beneficiario Solicitado con id: " + id);
        return ResponseEntity.ok(beneficiario);
    }




}
