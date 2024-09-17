package com.salud.arqui.logica;

import com.salud.arqui.db.jpa.HistorialMedicoJPA;
import com.salud.arqui.db.orm.HistorialMedicoORM;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Component
public class HistorialMedicoService {

    private final HistorialMedicoJPA historialMedicoJPA;

    public boolean guardarHistorialMedica(String tipoConsulta, String detalle){

        HistorialMedicoORM nuevoHistorialMedico = new HistorialMedicoORM();
        nuevoHistorialMedico.setTipo_consulta(tipoConsulta);
        nuevoHistorialMedico.setDetalle(detalle);
        historialMedicoJPA.save(nuevoHistorialMedico);
        return true;
    }

    public List<HistorialMedicoORM> listarHistorialMedico(){
        return historialMedicoJPA.findAll();
    }

    public HistorialMedicoORM buscarHistorialMedico(Long id) {
        return historialMedicoJPA.findById(id).orElse(null);
    }

}