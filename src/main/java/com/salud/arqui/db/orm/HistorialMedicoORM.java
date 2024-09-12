package com.salud.arqui.db.orm;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Table(name = "historialMedico")
@Entity
@Data
@NoArgsConstructor
public class HistorialMedicoORM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_historial_medico;


    @Column
    private String tipo_consulta;

    @Column
    private String detalle;

    @OneToMany
    @JoinColumn(name = "historiaMedico")
    private List<CitaMedicaORM> citasMedicas;






}
