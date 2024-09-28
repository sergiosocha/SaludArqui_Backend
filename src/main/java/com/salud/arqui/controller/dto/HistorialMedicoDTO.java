package com.salud.arqui.controller.dto;

import java.util.List;

public record HistorialMedicoDTO(Long idHistorialMedico, Long idAfiliado, Long idBeneficiario, List<Long> citasMedicasIds){

    public HistorialMedicoDTO(Long idHistorialMedico, Long idAfiliado, List<Long> citasMedicasIds) {
        this(idHistorialMedico, idAfiliado, null, citasMedicasIds);
    }

}