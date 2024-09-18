package com.salud.arqui.logica;

import com.salud.arqui.db.jpa.AfiliadoJPA;
import com.salud.arqui.db.jpa.BeneficiarioJPA;
import com.salud.arqui.db.jpa.CitaMedicaJPA;
import com.salud.arqui.db.jpa.HistorialMedicoJPA;
import com.salud.arqui.db.orm.AfiliadoORM;
import com.salud.arqui.db.orm.BeneficiarioORM;
import com.salud.arqui.db.orm.CitaMedicaORM;
import com.salud.arqui.db.orm.HistorialMedicoORM;
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
    private final AfiliadoJPA afiliadoJPA;
    private final BeneficiarioJPA beneficiarioJPA;
    private final HistorialMedicoJPA historialMedicoJPA;

    public boolean guardarCitaMedica(String tipoDeCita, String descripcion, LocalDate fechaConsulta,
                                     Long idAfiliado, Long idBeneficiario, Long idHistorialMedico) {

        AfiliadoORM afiliado = afiliadoJPA.findById(idAfiliado).orElseThrow(() ->
                new IllegalArgumentException("El ID del afiliado no existe."));

        BeneficiarioORM beneficiario = null;
        if (idBeneficiario != null) {
            beneficiario = beneficiarioJPA.findById(idBeneficiario).orElseThrow(() ->
                    new IllegalArgumentException("El ID del beneficiario no existe o ya está asignado a otro afiliado."));
        }

        HistorialMedicoORM historialMedico = historialMedicoJPA.findById(idHistorialMedico).orElseThrow(() ->
                new IllegalArgumentException("El ID del historial médico no existe."));

        CitaMedicaORM nuevaCitaMedica = new CitaMedicaORM();
        nuevaCitaMedica.setDescripcion(descripcion);
        nuevaCitaMedica.setFechaConsulta(fechaConsulta);
        nuevaCitaMedica.setTipoDeCita(tipoDeCita);
        nuevaCitaMedica.setAfiliadoORM(afiliado);
        nuevaCitaMedica.setBeneficiarioORM(beneficiario);
        nuevaCitaMedica.setHistorialMedicoORM(historialMedico);

        citaMedicaJPA.save(nuevaCitaMedica);


        historialMedico.getCitasMedicas().add(nuevaCitaMedica);
        historialMedicoJPA.save(historialMedico);

        return true;
    }

    public List<CitaMedicaORM> listaCitasMedicas() {
        return citaMedicaJPA.findAll();
    }

    public void eliminarCitaMedica(Long id) {
        citaMedicaJPA.deleteById(id);
    }

    public CitaMedicaORM getCitaMedicaById(Long id) {
        return citaMedicaJPA.findById(id).orElse(null);
    }
}