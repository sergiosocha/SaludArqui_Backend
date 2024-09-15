package com.salud.arqui.controller;


import com.salud.arqui.controller.dto.AfiliadoDTO;
import com.salud.arqui.db.orm.AfiliadoORM;
import com.salud.arqui.logica.AfiliadoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class AfiliadoController {

    private AfiliadoService afiliadoService;

    List<AfiliadoDTO> afiliados = new ArrayList<>();

    @PostMapping(path = "/afiliado")
    public String guardarAfiliado(@RequestBody AfiliadoDTO afiliadoDTO){
        afiliadoService.guardarAfiliado(afiliadoDTO.nombre(), afiliadoDTO.edad(),afiliadoDTO.email(),afiliadoDTO.genero());
        return "afiliado guardado con exito";
    }

    @GetMapping(path = "/afiliado/todos")
    public List<AfiliadoORM> obtenerAfiliados(){
        return afiliadoService.listaAfiliados();
    }

    @DeleteMapping(path = "/afiliado/{id}")
    public String eliminarAfiliado(@PathVariable Long id){
        afiliadoService.eliminarAfiliado(id);
        return "El afiliado se ha eliminado con exito";
    }

    @GetMapping(path = "/afiliado/{id}")
    public ResponseEntity<AfiliadoORM> obtenerAfiliadoId(@PathVariable Long id){
        AfiliadoORM afiliado = afiliadoService.buscarAfiliadoId(id);
        if(afiliado == null)
            System.out.println("No se encontro el afiliado Solicitado con id: " + id)
                    ;
        return ResponseEntity.ok(afiliado);

    }





}
