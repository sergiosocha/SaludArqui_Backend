package com.salud.arqui.logica;


import com.salud.arqui.db.jpa.AfiliadoJPA;
import com.salud.arqui.db.orm.AfiliadoORM;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AfiliadoService {

    private final AfiliadoJPA afiliadoJPA;


    public boolean guardarAfiliado(String nombre, Integer edad, String email, String genero){
        AfiliadoORM nuevoAfiliado = new AfiliadoORM();
        nuevoAfiliado.setNombre(nombre);
        nuevoAfiliado.setEdad(edad);
        nuevoAfiliado.setEmail(email);
        nuevoAfiliado.setGenero(genero);
        afiliadoJPA.save(nuevoAfiliado);
        return true;
    }


    public List<AfiliadoORM> listaAfiliados(){
        return afiliadoJPA.findAll();
    }

    public void eliminarAfiliado(Long id){
        afiliadoJPA.deleteById(id);
    }

    public AfiliadoORM buscarAfiliadoId(Long id){
        return afiliadoJPA.findById(id).orElse(null);
    }








}
