package com.salud.arqui.db.orm;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "historialMedico")
@Data
@NoArgsConstructor
public class HistorialMedicoORM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String tipoConsulta;

    @Column
    private String detalle;

    @Column
    private LocalDate fechaConsulta;




}
