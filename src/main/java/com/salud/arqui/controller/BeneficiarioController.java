package com.salud.arqui.controller;


import com.salud.arqui.controller.dto.BeneficiarioDTO;
import com.salud.arqui.logica.AfiliadoService;
import com.salud.arqui.logica.BeneficiarioService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BeneficiarioController {

    private BeneficiarioService beneficiarioService;


    @PostMapping(path = "/beneficiario")
    public String guardarBeneficiario(@RequestBody BeneficiarioDTO beneficiarioDTO){
        beneficiarioService.guardarBeneficiario(beneficiarioDTO.nombre(),beneficiarioDTO.email(),beneficiarioDTO.idAfiliado());
        return "Se ha registrado el beneficiario correctamente";
    }
}
