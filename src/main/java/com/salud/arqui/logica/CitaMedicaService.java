package com.salud.arqui.logica;

import com.salud.arqui.db.jpa.AfiliadoJPA;
import com.salud.arqui.db.jpa.BeneficiarioJPA;
import com.salud.arqui.db.jpa.CitaMedicaJPA;
import com.salud.arqui.db.orm.AfiliadoORM;
import com.salud.arqui.db.orm.BeneficiarioORM;
import com.salud.arqui.db.orm.CitaMedicaORM;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Component
public class CitaMedicaService {

    private final CitaMedicaJPA citaMedicaJPA;
    private final AfiliadoJPA afiliadoJPA;
    private final BeneficiarioJPA beneficiarioJPA;

    public boolean guardarCitaMedica(String tipoDeCita, String descripcion, LocalDate fechaConsulta, Long idAfiliado,
                                     Long idBeneficiario) {


        if (idAfiliado == null) {
            throw new IllegalArgumentException("El ID del afiliado no puede ser nulo.");
        }

        if (idBeneficiario == null) {
            throw new IllegalArgumentException("El ID del beneficiario no puede ser nulo.");
        }

        AfiliadoORM afiliado = afiliadoJPA.findById(idAfiliado).orElseThrow(()->
                new IllegalArgumentException("El ID del afiliado no existe."));

        BeneficiarioORM beneficiario = beneficiarioJPA.findById(idBeneficiario).orElseThrow(()->
                new IllegalArgumentException("El ID del beneficiario no existe."));

        CitaMedicaORM nuevaCitaMedica = new CitaMedicaORM();
        nuevaCitaMedica.setDescripcion(descripcion);
        nuevaCitaMedica.setFecha_consulta(fechaConsulta);
        nuevaCitaMedica.setTipo_de_cita(tipoDeCita);
        nuevaCitaMedica.setAfiliadoORM(afiliado);
        nuevaCitaMedica.setBeneficiarioORM(beneficiario);

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
