package com.salud.arqui.logica;


import com.salud.arqui.db.jpa.AfiliadoJPA;
import com.salud.arqui.db.jpa.HistorialMedicoJPA;
import com.salud.arqui.db.orm.AfiliadoORM;
import com.salud.arqui.db.orm.HistorialMedicoORM;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AfiliadoService {

    private final AfiliadoJPA afiliadoJPA;
    private final HistorialMedicoJPA historialMedicoJPA;

    public boolean guardarAfiliado(String nombre, Integer edad, String email, String genero)
    {
        AfiliadoORM nuevoAfiliado = new AfiliadoORM();
        nuevoAfiliado.setNombre(nombre);
        nuevoAfiliado.setEdad(edad);
        nuevoAfiliado.setEmail(email);
        nuevoAfiliado.setGenero(genero);
        afiliadoJPA.save(nuevoAfiliado);

        HistorialMedicoORM nuevoHistorial = new HistorialMedicoORM();
        nuevoHistorial.setAfiliadoORM(nuevoAfiliado);
        historialMedicoJPA.save(nuevoHistorial);

        return true;
    }


    public List<AfiliadoORM> listaAfiliados()
    {
        return afiliadoJPA.findAll();
    }

    public void eliminarAfiliado(Long id){
        afiliadoJPA.deleteById(id);

    }

    public AfiliadoORM buscarAfiliadoId(Long id)
    {
        return afiliadoJPA.findById(id).orElse(null);
    }

    public boolean actualizarAfiliado(Long id, String nombre, Integer edad, String email, String genero) {
        Optional<AfiliadoORM> afiliadoExistente = afiliadoJPA.findById(id);

        if (afiliadoExistente.isEmpty()) {
           log.info("El afiliado con id " + id + " no existe");
           return false;
        }

        AfiliadoORM afiliado = afiliadoExistente.get();
        afiliado.setNombre(nombre);
        afiliado.setEdad(edad);
        afiliado.setEmail(email);
        afiliado.setGenero(genero);
        afiliadoJPA.save(afiliado);

        return true;
    }
}
