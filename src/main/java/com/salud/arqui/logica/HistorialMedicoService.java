package com.salud.arqui.logica;

import com.salud.arqui.db.jpa.AfiliadoJPA;
import com.salud.arqui.db.jpa.BeneficiarioJPA;
import com.salud.arqui.db.jpa.HistorialMedicoJPA;
import com.salud.arqui.db.orm.AfiliadoORM;
import com.salud.arqui.db.orm.BeneficiarioORM;
import com.salud.arqui.db.orm.HistorialMedicoORM;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Component
public class HistorialMedicoService {
    private final HistorialMedicoJPA historialMedicoJPA;
    private final AfiliadoJPA afiliadoJPA;
    private final BeneficiarioJPA beneficiarioJPA;


    public HistorialMedicoORM crearHistorialParaAfiliado(Long idAfiliado) {
        AfiliadoORM afiliado = afiliadoJPA.findById(idAfiliado).orElseThrow(() ->
                new IllegalArgumentException("El afiliado no existe"));

        HistorialMedicoORM nuevoHistorial = new HistorialMedicoORM();
        nuevoHistorial.setAfiliadoORM(afiliado);
        historialMedicoJPA.save(nuevoHistorial);

        return nuevoHistorial;
    }

    public HistorialMedicoORM crearHistorialParaBeneficiario(Long idBeneficiario) {
        BeneficiarioORM beneficiario = beneficiarioJPA.findById(idBeneficiario).orElseThrow(() ->
                new IllegalArgumentException("El beneficiario no existe"));

        HistorialMedicoORM nuevoHistorial = new HistorialMedicoORM();
        nuevoHistorial.setBeneficiarioORM(beneficiario);
        historialMedicoJPA.save(nuevoHistorial);

        return nuevoHistorial;
    }


    public HistorialMedicoORM buscarHistorialMedicoPorAfiliado(Long idAfiliado) {
        return historialMedicoJPA.findByAfiliadoORM_idAfiliado(idAfiliado)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el historial médico para el afiliado con ID: " + idAfiliado));
    }

    public HistorialMedicoORM buscarHistorialMedicoPorBeneficiario(Long idBeneficiario) {
        return historialMedicoJPA.findByBeneficiarioORM_idBeneficiario(idBeneficiario)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el historial médico para el beneficiario con ID: " + idBeneficiario));
    }
}
