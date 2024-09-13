package com.salud.arqui.controller;


import com.salud.arqui.controller.dto.AfiliadoDTO;
import com.salud.arqui.logica.AfiliadoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AfiliadoController {

    private AfiliadoService afiliadoService;

    @PostMapping(path = "/afiliado")
    public String guardarAfiliado(@RequestBody AfiliadoDTO afiliadoDTO){
        afiliadoService.guardarAfiliado(afiliadoDTO.nombre(), afiliadoDTO.edad(),afiliadoDTO.email(),afiliadoDTO.genero());
        return "afiliado guardado con exito";
    }

}
