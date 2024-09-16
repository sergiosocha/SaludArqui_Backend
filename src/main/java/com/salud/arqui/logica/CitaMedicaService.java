package com.salud.arqui.logica;

import com.salud.arqui.db.jpa.AfiliadoJPA;
import com.salud.arqui.db.jpa.CitaMedicaJPA;
import com.salud.arqui.db.orm.CitaMedicaORM;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Component
public class CitaMedicaService {

    private final CitaMedicaJPA citaMedicaJPA;

    public boolean guardarCitaMedica(String tipoDeCita, String descripcion, LocalDate fechaConsulta){

        CitaMedicaORM nuevaCitaMedica = new CitaMedicaORM();
        nuevaCitaMedica.setDescripcion(descripcion);
        nuevaCitaMedica.setFecha_consulta(fechaConsulta);
        nuevaCitaMedica.setTipo_de_cita(tipoDeCita);
        citaMedicaJPA.save(nuevaCitaMedica);
        return true;
    }

    public List<CitaMedicaORM> listaCitasMedicas(){
        return citaMedicaJPA.findAll();
    }

    public void eliminarCitaMedica(Long id){
        citaMedicaJPA.deleteById(id);
    }

    public CitaMedicaORM getCitaMedicaById(Long id){
        CitaMedicaORM citaMedica = citaMedicaJPA.findById(id).orElse(null);
        return citaMedica;
    }

}
